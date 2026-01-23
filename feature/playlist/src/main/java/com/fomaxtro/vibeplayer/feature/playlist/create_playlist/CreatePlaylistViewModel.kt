package com.fomaxtro.vibeplayer.feature.playlist.create_playlist

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.core.ui.util.getTextFieldState
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.model.Playlist
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import com.fomaxtro.vibeplayer.feature.playlist.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    savedStateHandle: SavedStateHandle,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private companion object {
        const val PLAYLIST_NAME_KEY = "playlist_name"
    }

    private val eventChannel = Channel<CreatePlaylistEvent>()
    val events = eventChannel.receiveAsFlow()

    private val playlistName = savedStateHandle.getTextFieldState(
        scope = viewModelScope,
        key = PLAYLIST_NAME_KEY,
        initialValue = ""
    )

    val state: StateFlow<CreatePlaylistState> = snapshotFlow {
        playlistName.text.toString()
    }
        .map { playlistName ->
            CreatePlaylistState(
                playlistName = this.playlistName,
                canSubmit = Playlist.isValidName(playlistName)
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = CreatePlaylistState(
                playlistName = playlistName
            )
        )

    fun onAction(action: CreatePlaylistAction) {
        when (action) {
            CreatePlaylistAction.OnCreateClick -> createPlaylist()
            CreatePlaylistAction.OnCancelClick -> cancel()
        }
    }

    private fun cancel() = viewModelScope.launch {
        playlistName.clearText()
        eventChannel.send(CreatePlaylistEvent.Cancel)
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
                val message = when (result.error) {
                    DataError.Resource.ALREADY_EXISTS -> {
                        UiText.StringResource(R.string.error_playlist_already_exists)
                    }

                    else -> result.error.toUiText()
                }

                eventChannel.send(CreatePlaylistEvent.ShowMessage(message))
            }

            is Result.Success -> {
                playlistName.clearText()
                eventChannel.send(
                    CreatePlaylistEvent.PlaylistCreated(
                        playlistId = result.data
                    )
                )
            }
        }
    }
}