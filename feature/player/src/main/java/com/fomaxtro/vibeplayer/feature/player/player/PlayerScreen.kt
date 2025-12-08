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

                    is PlayerUiState.Success -> {
                        SubcomposeAsyncImage(
                            model = state.song.albumArtUri,
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
                            text = state.song.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = state.song.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    }
                }
            }

            PlaybackControls(
                progress = 0.5f,
                playing = true,
                onPlayPauseToggle = {},
                onSkipPreviousClick = {},
                onSkipNextClick = {},
                modifier = Modifier.fillMaxWidth(),
                canSkipPrevious = state !is PlayerUiState.Loading,
                canSkipNext = state !is PlayerUiState.Loading,
                canPlayPause = state !is PlayerUiState.Loading
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
                song = Song(
                    id = 1,
                    title = "505",
                    artist = "Arctic Monkeys",
                    durationMillis = 180000,
                    filePath = "",
                    sizeBytes = 100 * 1024,
                    albumArtUri = ""
                )
            )
        )
    }
}