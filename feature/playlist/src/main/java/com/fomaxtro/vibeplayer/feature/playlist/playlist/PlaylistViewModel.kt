package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.use_case.ObservePlaylistSummary
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistViewModel(
    savedStateHandle: SavedStateHandle,
    observePlaylistSummary: ObservePlaylistSummary
) : ViewModel() {
    private companion object {
        const val PLAYLIST_SHEET_VISIBLE_KEY = "playlist_sheet_visible"
    }

    private val eventChannel = Channel<PlaylistEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isCreatePlaylistSheetOpen = savedStateHandle.getMutableStateFlow(
        key = PLAYLIST_SHEET_VISIBLE_KEY,
        initialValue = false
    )

    val state: StateFlow<PlaylistUiState> = combine(
        observePlaylistSummary(),
        isCreatePlaylistSheetOpen
    ) { playlistSummary, isCreatePlaylistSheetOpen ->
        PlaylistUiState.Success(
            isCreatePlaylistSheetOpen = isCreatePlaylistSheetOpen,
            playlists = playlistSummary.playlists,
            favouriteSongsCount = playlistSummary.favouriteSongsCount
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlaylistUiState.Loading
        )

    fun onAction(action: PlaylistAction) {
        when (action) {
            PlaylistAction.OnAddPlaylistClick -> showCreatePlaylistSheet()
            PlaylistAction.OnDismissPlaylistCreateSheet -> dismissCreatePlaylistSheet()
            is PlaylistAction.OnPlaylistCreated -> onPlaylistCreated(action.playlistId)
        }
    }

    private fun onPlaylistCreated(playlistId: Long) = viewModelScope.launch {
        dismissCreatePlaylistSheet()
        eventChannel.send(PlaylistEvent.PlaylistCreated(playlistId))
    }

    private fun dismissCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = false
    }

    private fun showCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = true
    }
}