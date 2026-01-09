package com.fomaxtro.vibeplayer.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import com.fomaxtro.vibeplayer.feature.home.model.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    observeSongs: ObserveSongs,
    private val player: MusicPlayer
) : ViewModel() {
    private val songs = observeSongs()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )
    private val isPlayerExpanded = MutableStateFlow(false)
    private val selectedTabIndex = MutableStateFlow(Destination.SONGS.ordinal)

    val state = combine(
        songs,
        isPlayerExpanded,
        selectedTabIndex
    ) { songs, isPlayerExpanded, selectedTabIndex ->
        HomeUiState(
            songs = songs,
            isPlayerExpanded = isPlayerExpanded,
            selectedTabIndex = selectedTabIndex
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
            is HomeAction.OnTabSelected -> onTabSelected(action.index)
            else -> Unit
        }
    }

    private fun onTabSelected(index: Int) {
        selectedTabIndex.value = index
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
            player.setPlaylist(songs.value.shuffled())
            player.play(songs.value.indices.random())

            isPlayerExpanded.value = true
        }
    }

    private fun playSongWithPlaylist(index: Int) {
        if (index != -1) {
            player.play(
                playlist = songs.value,
                index = index
            )

            onExpandPlayer()
        }
    }

    private fun onPlayPlaylistClick() {
        if (songs.value.isNotEmpty()) {
            playSongWithPlaylist(0)
        }
    }

    private fun onSongClick(song: Song) = viewModelScope.launch {
        val songIndex = songs.value.indexOf(song)

        playSongWithPlaylist(songIndex)
    }
}