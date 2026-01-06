package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Playlist

@Immutable
sealed interface PlaylistUiState {
    data object Loading : PlaylistUiState

    data class Success(
        val playlists: List<Playlist> = emptyList()
    ) : PlaylistUiState
}
