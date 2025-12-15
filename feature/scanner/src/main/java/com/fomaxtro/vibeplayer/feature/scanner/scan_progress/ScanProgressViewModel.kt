package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanProgressViewModel(
    private val songRepository: SongRepository
) : ViewModel() {
    private val eventChannel = Channel<ScanProgressEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isScanning = MutableStateFlow(true)
    val state = isScanning
        .map { isScanning ->
            if (isScanning) {
                ScanProgressUiState.Scanning
            } else {
                ScanProgressUiState.Empty
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ScanProgressUiState.Scanning
        )

    private fun scanSongs() = viewModelScope.launch {
        isScanning.update { true }

        try {
            when (val result = songRepository.scanSongs()) {
                is Result.Error -> {
                    eventChannel.send(
                        ScanProgressEvent.ShoMessage(result.error.toUiText())
                    )
                }

                is Result.Success -> {
                    if (result.data > 0) {
                        eventChannel.send(ScanProgressEvent.ScanFinish)
                    }
                }
            }
        } finally {
            isScanning.update { false }
        }
    }

    init {
        scanSongs()
    }

    fun onAction(action: ScanProgressAction) {
        when (action) {
            ScanProgressAction.OnScanAgainClick -> scanSongs()
            else -> Unit
        }
    }
}