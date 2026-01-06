package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.fomaxtro.vibeplayer.core.ui.preview.SongPreviewFactory
import com.fomaxtro.vibeplayer.domain.player.RepeatMode
import kotlin.time.Duration.Companion.seconds

class PlayerPreviewParameterProvider : PreviewParameterProvider<PlayerUiState> {
    override val values: Sequence<PlayerUiState> = sequenceOf(
        PlayerUiState(
            playingSong = SongPreviewFactory.makeSong()
        ),
        PlayerUiState(
            playingSong = SongPreviewFactory.makeSong(),
            isPlaying = true,
            repeatMode = RepeatMode.ONE,
            playingSongPosition = 90.seconds
        ),
        PlayerUiState(
            playingSong = SongPreviewFactory.makeSong(),
            isPlaying = false,
            canSkipNext = false,
            canSkipPrevious = false
        ),
        PlayerUiState(
            playingSong = SongPreviewFactory.makeSong(),
            repeatMode = RepeatMode.ALL,
            isShuffleEnabled = true
        )
    )
}