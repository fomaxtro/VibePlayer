package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Playlist

@Immutable
sealed interface PlaylistUiState {
    data object Loading : PlaylistUiState

    data class Success(
        val playlists: List<Playlist> = emptyList(),
        val isCreatePlaylistSheetOpen: Boolean = false,
        val playlistName: TextFieldState,
        val canCreatePlaylist: Boolean = false
    ) : PlaylistUiState
}
