package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeAlbumArt
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.player.component.PlaybackControls
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun PlayerScreen(
    songIndex: Int,
    onNavigateBack: () -> Unit,
    viewModel: PlayerViewModel = koinViewModel {
        parametersOf(songIndex)
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PlayerScreen(
        state = state,
        onAction = { action -> 
            when (action) {
                PlayerAction.OnNavigateBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun PlayerScreen(
    state: PlayerUiState,
    onAction: (PlayerAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                onNavigateBackClick = {
                    onAction(PlayerAction.OnNavigateBackClick)
                }
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
                VibeAlbumArt(
                    imageUrl = state.playingSong?.albumArtUri,
                    contentDescription = null,
                    modifier = Modifier
                        .then(
                            if (isWideScreen) {
                                Modifier.size(320.dp)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp)
                            }
                        )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = state.playingSong?.title ?: "",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = state.playingSong?.artist ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            PlaybackControls(
                progress = state.currentSongProgress,
                playing = state.isPlaying,
                onPlayPauseToggle = {
                    onAction(PlayerAction.OnPlayPauseToggle)
                },
                onSkipPreviousClick = {
                    onAction(PlayerAction.OnSkipPreviousClick)
                },
                onSkipNextClick = {
                    onAction(PlayerAction.OnSkipNextClick)
                },
                modifier = Modifier.fillMaxWidth(),
                canSkipPrevious = state.canSkipPrevious,
                canSkipNext = state.canSkipNext,
                canPlayPause = state.playingSong != null
            )
        }
    }
}

@DevicePreviews
@Composable
private fun PLayerScreenPreview() {
    VibePlayerTheme {
        PlayerScreen(
            state = PlayerUiState(
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