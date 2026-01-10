package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.core.ui.util.getTextFieldState
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistViewModel(
    savedStateHandle: SavedStateHandle,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private companion object {
        const val PLAYLIST_SHEET_VISIBLE_KEY = "playlist_sheet_visible"
        const val PLAYLIST_NAME_KEY = "playlist_name"
    }

    private val eventChannel = Channel<PlaylistEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isCreatePlaylistSheetOpen = savedStateHandle.getMutableStateFlow(
        key = PLAYLIST_SHEET_VISIBLE_KEY,
        initialValue = false
    )
    private val playlistName = savedStateHandle.getTextFieldState(
        scope = viewModelScope,
        key = PLAYLIST_NAME_KEY,
        initialValue = ""
    )

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
            PlaylistAction.OnAddPlaylistClick -> showCreatePlaylistSheet()
            PlaylistAction.OnDismissPlaylistCreateSheet -> dismissCreatePlaylistSheet()
            PlaylistAction.OnConfirmCreatePlaylist -> createPlaylist()
        }
    }

    private fun createPlaylist() = viewModelScope.launch {
        when (
            val result = playlistRepository.createPlaylist(
                NewPlaylist(
                    name = playlistName.text.toString()
                )
            )
        ) {
            is Result.Error -> {
                eventChannel.send(
                    PlaylistEvent.ShowSystemMessage(
                        message = result.error.toUiText()
                    )
                )
            }

            is Result.Success -> {
                dismissCreatePlaylistSheet()

                eventChannel.send(
                    PlaylistEvent.PlaylistCreated(
                        playlistId = result.data
                    )
                )
            }
        }
    }

    private fun dismissCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = false
    }

    private fun showCreatePlaylistSheet() {
        isCreatePlaylistSheetOpen.value = true
    }
}