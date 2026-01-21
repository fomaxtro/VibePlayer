package com.fomaxtro.vibeplayer.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.mapper.toMediaTrack
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import com.fomaxtro.vibeplayer.feature.home.model.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    observeSongs: ObserveSongs,
    private val player: MusicPlayer
) : ViewModel() {
    private val songs: StateFlow<List<Song>> = observeSongs()
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
            HomeAction.OnCollapsePlayer -> collapsePlayer()
            HomeAction.OnExpandPlayer -> expandPlayer()
            HomeAction.OnPlayPlaylistClick -> playPlaylist()
            HomeAction.OnShufflePlaylistClick -> shufflePlaylist()
            is HomeAction.OnSongClick -> playSong(action.song)
            is HomeAction.OnTabSelected -> selectTab(action.index)
            else -> Unit
        }
    }

    private fun selectTab(index: Int) {
        selectedTabIndex.value = index
    }

    private fun expandPlayer() = viewModelScope.launch {
        delay(100)
        isPlayerExpanded.value = true
    }

    private fun collapsePlayer() {
        isPlayerExpanded.value = false
    }

    private fun shufflePlaylist() {
        if (songs.value.isNotEmpty()) {
            val playlist = songs.value.toMediaTrack()

            player.setPlaylist(playlist.shuffled())
            player.play(playlist.indices.random())

            isPlayerExpanded.value = true
        }
    }

    private fun playSongWithPlaylist(index: Int) {
        if (index != -1) {
            player.play(
                playlist = songs.value.toMediaTrack(),
                index = index
            )

            expandPlayer()
        }
    }

    private fun playPlaylist() {
        if (songs.value.isNotEmpty()) {
            playSongWithPlaylist(0)
        }
    }

    private fun playSong(song: Song) = viewModelScope.launch {
        val songIndex = songs.value.toMediaTrack()
            .indexOfFirst { it.id == song.id }

        playSongWithPlaylist(songIndex)
    }
}