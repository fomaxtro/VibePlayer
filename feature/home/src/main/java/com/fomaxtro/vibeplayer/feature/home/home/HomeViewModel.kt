package com.fomaxtro.vibeplayer.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    observeSongs: ObserveSongs,
    private val player: MusicPlayer
) : ViewModel() {
    private val songs = observeSongs()
        .onEach { playlist ->
            player.setPlaylist(playlist)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )
    private val isPlayerExpanded = MutableStateFlow(false)

    val state = combine(
        songs,
        isPlayerExpanded
    ) { songs, isPlayerExpanded ->
        HomeUiState(
            songs = songs,
            isPlayerExpanded = isPlayerExpanded
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HomeUiState()
    )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnCollapsePlayer -> onCollapsePlayer()
            HomeAction.OnExpandPlayer -> onExpandPlayer()
            HomeAction.OnPlayPlaylistClick -> onPlayPlaylistClick()
            HomeAction.OnShufflePlaylistClick -> onShufflePlaylistClick()
            is HomeAction.OnSongClick -> onSongClick(action.song)
            else -> Unit
        }
    }

    private fun onExpandPlayer() = viewModelScope.launch {
        delay(100)
        isPlayerExpanded.value = true
    }

    private fun onCollapsePlayer() {
        isPlayerExpanded.value = false
    }

    private fun onShufflePlaylistClick() {
        if (songs.value.isNotEmpty()) {
            player.playFirstShuffled()

            isPlayerExpanded.value = true
        }
    }

    private fun onPlayPlaylistClick() {
        if (songs.value.isNotEmpty()) {
            player.setShuffleModeEnabled(false)
            player.play(
                playlist = songs.value,
                index = 0
            )

            onExpandPlayer()
        }
    }

    private fun onSongClick(song: Song) = viewModelScope.launch {
        val songIndex = songs.value.indexOf(song)

        if (songIndex != -1) {
            player.play(
                playlist = songs.value,
                index = songIndex
            )

            onExpandPlayer()
        }
    }
}