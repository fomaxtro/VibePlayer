package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.fomaxtro.vibeplayer.core.ui.preview.PlaylistPreviewFactory

class PlaylistPreviewParameterProvider : PreviewParameterProvider<PlaylistUiState> {
    override val values: Sequence<PlaylistUiState> = sequenceOf(
        PlaylistUiState.Loading,
        PlaylistUiState.Success(
            favouriteSongsCount = 1
        ),
        PlaylistUiState.Success(
            playlists = PlaylistPreviewFactory.defaultList,
            favouriteSongsCount = 3
        )
    )
}