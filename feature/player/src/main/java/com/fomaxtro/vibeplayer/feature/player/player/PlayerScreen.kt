package com.fomaxtro.vibeplayer.feature.player.player

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeAlbumArt
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButtonDefaults
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibePlayPauseButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.RepeatMode
import com.fomaxtro.vibeplayer.feature.player.R
import com.fomaxtro.vibeplayer.feature.player.component.PlaybackSlider
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Composable
fun PlayerScreen(
    onNavigateBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: PlayerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is PlayerEvent.ShowSystemMessage -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    PlayerScreen(
        state = state,
        onAction = { action ->
            when (action) {
                PlayerAction.OnNavigateBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        },
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

    @Composable
private fun PlayerScreen(
    state: PlayerUiState,
    onAction: (PlayerAction) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                navigationIcon = {
                    VibeIconButton(
                        onClick = {
                            onAction(PlayerAction.OnNavigateBackClick)
                        }
                    ) {
                        Icon(
                            imageVector = VibeIcons.Filled.ChevronDown,
                            contentDescription = stringResource(DesignR.string.navigate_back)
                        )
                    }
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
            with(sharedTransitionScope) {
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
                                        .sharedElement(
                                            rememberSharedContentState("album_art"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        )
                                }
                            )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = state.playingSong?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.sharedBounds(
                            rememberSharedContentState("song_title"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.playingSong?.artist ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState("song_artist"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlaybackSlider(
                        value = state.playingSongProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        progressText = { factor ->
                            if (state.playingSong != null) {
                                val totalDuration = state.playingSong.duration

                                val currentProgress = (totalDuration.inWholeMilliseconds * factor)
                                    .roundToInt()
                                    .milliseconds

                                "${currentProgress.formatDuration()} / ${totalDuration.formatDuration()}"
                            } else ""
                        },
                        onSeek = { factor ->
                            onAction(PlayerAction.OnSeekTo(factor))
                        },
                        onSeekStarted = {
                            onAction(PlayerAction.OnSeekStarted)
                        },
                        onSeekCancel = {
                            onAction(PlayerAction.OnSeekCancel)
                        },
                        seeking = state.isSeeking
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VibeIconButton(
                            onClick = {
                                onAction(PlayerAction.OnToggleShuffleClick)
                            },
                            modifier = Modifier.size(44.dp),
                            colors = VibeIconButtonDefaults.colors(
                                containerColor = if (state.isShuffleEnabled) {
                                    MaterialTheme.colorScheme.buttonHover
                                } else {
                                    Color.Transparent
                                }
                            )
                        ) {
                            Icon(
                                imageVector = VibeIcons.Outlined.Shuffle,
                                contentDescription = stringResource(R.string.shuffle),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            VibeIconButton(
                                onClick = {
                                    onAction(PlayerAction.OnSkipPreviousClick)
                                },
                                modifier = Modifier.size(44.dp),
                                enabled = state.canSkipPrevious
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.SkipPrevious,
                                    contentDescription = stringResource(R.string.skip_previous),
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            VibePlayPauseButton(
                                onClick = {
                                    onAction(PlayerAction.OnPlayPauseToggle)
                                },
                                playing = state.isPlaying,
                                modifier = Modifier
                                    .size(60.dp)
                                    .sharedBounds(
                                        rememberSharedContentState("play_pause"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    ),
                                enabled = state.playingSong != null
                            )

                            VibeIconButton(
                                onClick = {
                                    onAction(PlayerAction.OnSkipNextClick)
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .sharedBounds(
                                        rememberSharedContentState("skip_next"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    ),
                                enabled = state.canSkipNext
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.SkipNext,
                                    contentDescription = stringResource(R.string.skip_next),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        VibeIconButton(
                            onClick = {
                                onAction(PlayerAction.OnRepeatModeClick)
                            },
                            modifier = Modifier.size(44.dp),
                            colors = VibeIconButtonDefaults.colors(
                                containerColor = if (state.repeatMode == RepeatMode.OFF) {
                                    Color.Transparent
                                } else {
                                    MaterialTheme.colorScheme.buttonHover
                                }
                            )
                        ) {
                            Icon(
                                imageVector = when (state.repeatMode) {
                                    RepeatMode.OFF -> VibeIcons.Outlined.RepeatOff
                                    RepeatMode.ALL -> VibeIcons.Outlined.RepeatAll
                                    RepeatMode.ONE -> VibeIcons.Outlined.RepeatOne
                                },
                                contentDescription = when (state.repeatMode) {
                                    RepeatMode.OFF -> stringResource(R.string.repeat_off)
                                    RepeatMode.ALL -> stringResource(R.string.repeat_all)
                                    RepeatMode.ONE -> stringResource(R.string.repeat_one)
                                },
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@DevicePreviews
@Composable
private fun PLayerScreenPreview() {
    VibePlayerTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true
            ) {
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
                        playingSongPosition = 30.seconds
                    ),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent
                )
            }
        }
    }
}