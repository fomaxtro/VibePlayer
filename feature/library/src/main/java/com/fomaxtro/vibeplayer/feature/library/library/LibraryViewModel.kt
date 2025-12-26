package com.fomaxtro.vibeplayer.feature.library.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(
    observeSongs: ObserveSongs,
    private val player: MusicPlayer
) : ViewModel() {
    private val songs = observeSongs()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val state = songs
        .map { songs ->
            LibraryUiState(
                songs = songs
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LibraryUiState()
        )

    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.OnSongClick -> onSongClick(action.song)
            LibraryAction.OnPlayClick -> onPlayClick()
            LibraryAction.OnShuffleClick -> onShuffleClick()
            else -> Unit
        }
    }

    private fun onShuffleClick() {
        if (songs.value.isNotEmpty()) {
            player.setPlaylist(songs.value)
            player.playFirstShuffled()
        }
    }

    private fun onPlayClick() {
        if (songs.value.isNotEmpty()) {
            player.setShuffleModeEnabled(false)
            player.play(
                playlist = songs.value,
                index = 0
            )
        }
    }

    private fun onSongClick(song: Song) = viewModelScope.launch {
        val songIndex = songs.value.indexOf(song)

        if (songIndex != -1) {
            player.play(
                playlist = songs.value,
                index = songIndex
            )
        }
    }
}