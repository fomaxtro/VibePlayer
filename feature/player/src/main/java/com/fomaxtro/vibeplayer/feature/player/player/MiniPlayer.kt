package com.fomaxtro.vibeplayer.feature.player.player

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeAlbumArt
import com.fomaxtro.vibeplayer.core.designsystem.component.VibePlayPauseButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceHigher
import com.fomaxtro.vibeplayer.feature.player.R
import com.fomaxtro.vibeplayer.feature.player.component.PlaybackSlider

@Composable
fun MiniPlayer(
    albumArt: String?,
    title: String,
    artist: String,
    @FloatRange(from = 0.0, to = 1.0) playbackProgress: Float,
    onPlayPauseToggle: () -> Unit,
    onSkipNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp
    )
) {
    Surface(
        modifier = modifier
            .height(IntrinsicSize.Max),
        color = MaterialTheme.colorScheme.surfaceHigher,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VibeAlbumArt(
                imageUrl = albumArt,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VibePlayPauseButton(
                            onClick = onPlayPauseToggle,
                            playing = false,
                            modifier = Modifier.size(44.dp)
                        )

                        IconButton(
                            onClick = onSkipNextClick,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = VibeIcons.Filled.SkipNext,
                                contentDescription = stringResource(R.string.skip_next),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                PlaybackSlider(
                    value = playbackProgress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun MiniPlayerPreview() {
    VibePlayerTheme {
        MiniPlayer(
            albumArt = null,
            modifier = Modifier.fillMaxWidth(),
            title = "505",
            artist = "Artic Monkeys",
            playbackProgress = 0.75f,
            onPlayPauseToggle = {},
            onSkipNextClick = {}
        )
    }
}