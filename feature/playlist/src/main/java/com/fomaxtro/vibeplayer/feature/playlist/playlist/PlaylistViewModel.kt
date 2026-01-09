package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlaylistViewModel : ViewModel() {
    private val playlistName = TextFieldState()
    private val isCreatePlaylistSheetOpen = MutableStateFlow(false)

    val state: StateFlow<PlaylistUiState> = isCreatePlaylistSheetOpen
        .map { isCreatePlaylistSheetOpen ->
            PlaylistUiState.Success(
                playlistName = playlistName,
                isCreatePlaylistSheetOpen = isCreatePlaylistSheetOpen,
                playlists = emptyList()
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlaylistUiState.Loading
        )

    fun onAction(action: PlaylistAction) {
        when (action) {
            PlaylistAction.OnCreatePlaylistClick -> showCreatePlaylistSheet()
            PlaylistAction.OnDismissPlaylistCreateSheet -> dismissCreatePlaylistSheet()
        }
    }

    private fun dismissCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = false
    }

    private fun showCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = true
    }
}