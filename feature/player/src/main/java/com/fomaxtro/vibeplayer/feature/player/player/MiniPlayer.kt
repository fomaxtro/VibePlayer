package com.fomaxtro.vibeplayer.feature.player.player

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
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
    state: PlayerUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onAction: (PlayerAction) -> Unit = {}
) {
    with(sharedTransitionScope) {
        Surface(
            modifier = modifier.height(IntrinsicSize.Max),
            color = MaterialTheme.colorScheme.surfaceHigher,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VibeAlbumArt(
                    imageUrl = state.playingSong?.albumArtUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .sharedElement(
                            rememberSharedContentState("album_art"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
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
                                text = state.playingSong?.title ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState("song_title"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                                    )
                                    .basicMarquee(),
                                maxLines = 1
                            )

                            Text(
                                text = state.playingSong?.artist ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .sharedElement(
                                        rememberSharedContentState("song_artist"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                                    .basicMarquee(),
                                maxLines = 1
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            VibePlayPauseButton(
                                onClick = {
                                    onAction(PlayerAction.OnPlayPauseToggle)
                                },
                                playing = state.isPlaying,
                                modifier = Modifier
                                    .size(44.dp)
                                    .sharedBounds(
                                        rememberSharedContentState("play_pause"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                    )
                            )

                            IconButton(
                                onClick = {
                                    onAction(PlayerAction.OnSkipNextClick)
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier
                                    .size(44.dp)
                                    .sharedBounds(
                                        rememberSharedContentState("skip_next"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
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
                        value = state.playingSongProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        containerColor = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
private fun MiniPlayerPreview(
    @PreviewParameter(PlayerPreviewParameterProvider::class, limit = 2) state: PlayerUiState
) {
    VibePlayerTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true
            ) {
                MiniPlayer(
                    state = state,
                    modifier = Modifier.fillMaxWidth(),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent
                )
            }
        }
    }
}