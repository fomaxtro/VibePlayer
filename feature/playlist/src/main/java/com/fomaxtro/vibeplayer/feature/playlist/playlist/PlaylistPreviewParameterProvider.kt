package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.fomaxtro.vibeplayer.core.ui.preview.PlaylistPreviewFactory

class PlaylistPreviewParameterProvider : PreviewParameterProvider<PlaylistUiState> {
    override val values: Sequence<PlaylistUiState> = sequenceOf(
        PlaylistUiState.Loading,
        PlaylistUiState.Success(
            playlistName = TextFieldState()
        ),
        PlaylistUiState.Success(
            playlists = PlaylistPreviewFactory.defaultList,
            playlistName = TextFieldState()
        )
    )
}