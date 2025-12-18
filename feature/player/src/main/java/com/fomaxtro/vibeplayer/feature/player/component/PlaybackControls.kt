package com.fomaxtro.vibeplayer.feature.player.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibePlayPauseButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.player.R

@Composable
fun PlaybackControls(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    playing: Boolean,
    onPlayPauseToggle: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    canSkipNext: Boolean = true,
    canSkipPrevious: Boolean = true,
    canPlayPause: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaybackSlider(
            value = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            VibeIconButton(
                onClick = onSkipPreviousClick,
                modifier = Modifier.size(44.dp),
                enabled = canSkipPrevious
            ) {
                Icon(
                    imageVector = VibeIcons.Filled.SkipPrevious,
                    contentDescription = stringResource(R.string.skip_previous),
                    modifier = Modifier.size(16.dp)
                )
            }

            VibePlayPauseButton(
                onClick = onPlayPauseToggle,
                playing = playing,
                modifier = Modifier.size(60.dp),
                enabled = canPlayPause
            )

            VibeIconButton(
                onClick = onSkipNextClick,
                modifier = Modifier.size(44.dp),
                enabled = canSkipNext
            ) {
                Icon(
                    imageVector = VibeIcons.Filled.SkipNext,
                    contentDescription = stringResource(R.string.skip_next),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlaybackControlsPreview() {
    VibePlayerTheme {
        PlaybackControls(
            modifier = Modifier.fillMaxWidth(),
            progress = 0.7f,
            playing = true,
            onPlayPauseToggle = {},
            onSkipNextClick = {},
            onSkipPreviousClick = {}
        )
    }
}