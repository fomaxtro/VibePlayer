package com.fomaxtro.vibeplayer.feature.library.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModel(
    observeSongs: ObserveSongs,
    private val player: MusicPlayer
) : ViewModel() {
    val state = observeSongs(viewModelScope)
        .map { songs ->
            if (songs.isEmpty()) {
                LibraryUiState.Empty
            } else {
                LibraryUiState.Success(
                    songs = songs
                )
            }
        }
        .onEach { state ->
            if (state is LibraryUiState.Success) {
                player.setPlaylist(state.songs)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LibraryUiState.Loading
        )

    override fun onCleared() {
        super.onCleared()

        player.clearPlaylist()
    }
}