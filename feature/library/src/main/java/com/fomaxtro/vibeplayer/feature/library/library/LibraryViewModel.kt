package com.fomaxtro.vibeplayer.feature.library.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModel(
    autoScan: Boolean,
    observeSongs: ObserveSongs,
    private val songRepository: SongRepository
) : ViewModel() {
    private val isScanning = MutableStateFlow(autoScan)
    val state: StateFlow<LibraryUiState> = isScanning
        .flatMapLatest { isScanning ->
            if (isScanning) {
                flowOf(LibraryUiState.Loading)
            } else {
                observeSongs(
                    scope = viewModelScope
                )
                    .map { songs ->
                        if (songs.isEmpty()) {
                            LibraryUiState.Empty
                        } else {
                            LibraryUiState.Success(
                                songs = songs
                            )
                        }
                    }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LibraryUiState.Loading
        )

    private fun scanSongs() = viewModelScope.launch {
        isScanning.value = true
        songRepository.scanSongs()
        isScanning.value = false
    }

    init {
        if (autoScan) {
            scanSongs()
        }
    }

    fun onAction(action: LibraryAction) {
        when (action) {
            LibraryAction.OnScanAgainClick -> scanSongs()
            else -> Unit
        }
    }
}