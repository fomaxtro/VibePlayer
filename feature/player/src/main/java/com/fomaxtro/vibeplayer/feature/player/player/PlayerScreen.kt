package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCircularProgressIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.player.component.PlaybackControls
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun PlayerScreen(
    state: PlayerUiState
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                onNavigateBackClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (state) {
                    PlayerUiState.Loading -> {
                        VibeCircularProgressIndicator()
                    }

                    is PlayerUiState.Success if state.playingSong != null -> {
                        SubcomposeAsyncImage(
                            model = state.playingSong.albumArtUri,
                            contentDescription = null,
                            error = {
                                VibeSongDefaultImage()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = state.playingSong.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = state.playingSong.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    }

                    else -> Unit
                }
            }

            val successState = state as? PlayerUiState.Success
            val isPlaying = successState?.isPlaying ?: false

            PlaybackControls(
                progress = successState?.currentSongProgress ?: 0f,
                playing = isPlaying,
                onPlayPauseToggle = {},
                onSkipPreviousClick = {},
                onSkipNextClick = {},
                modifier = Modifier.fillMaxWidth(),
                canSkipPrevious = successState?.canSkipPrevious ?: false,
                canSkipNext = successState?.canSkipNext ?: false,
                canPlayPause = successState != null
            )
        }
    }
}

@DevicePreviews
@Composable
private fun PLayerScreenPreview() {
    VibePlayerTheme {
        PlayerScreen(
            state = PlayerUiState.Success(
                playingSong = Song(
                    id = 1,
                    title = "505",
                    artist = "Arctic Monkeys",
                    duration = 2.minutes,
                    filePath = "",
                    sizeBytes = 100 * 1024,
                    albumArtUri = ""
                ),
                currentPosition = 30.seconds
            )
        )
    }
}