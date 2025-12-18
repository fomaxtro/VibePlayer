package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScanProgressViewModel(
    private val songRepository: SongRepository
) : ViewModel() {
    private val eventChannel = Channel<ScanProgressEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow<ScanProgressUiState>(ScanProgressUiState.Scanning)
    val state = _state.asStateFlow()

    private fun scanSongs() = viewModelScope.launch {
        _state.value = ScanProgressUiState.Scanning

        when (val result = songRepository.scanSongs()) {
            is Result.Error -> {
                eventChannel.send(
                    ScanProgressEvent.ShoMessage(result.error.toUiText())
                )

                _state.value = ScanProgressUiState.Empty
            }

            is Result.Success -> {
                if (result.data > 0) {
                    eventChannel.send(ScanProgressEvent.ScanFinish)
                } else {
                    _state.value = ScanProgressUiState.Empty
                }
            }
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