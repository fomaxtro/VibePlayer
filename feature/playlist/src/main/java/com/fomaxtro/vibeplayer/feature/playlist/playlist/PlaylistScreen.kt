package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCircularProgressIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeGradientIcon
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMediaCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.feature.playlist.R
import com.fomaxtro.vibeplayer.feature.playlist.create_playlist.CreatePlaylistSheet
import com.fomaxtro.vibeplayer.feature.playlist.playlist.component.MenuIconButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlaylistScreen(
    onPlaylistCreated: (playlistId: Long) -> Unit,
    viewModel: PlaylistViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is PlaylistEvent.PlaylistCreated -> onPlaylistCreated(event.playlistId)
        }
    }

    PlaylistScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlaylistScreen(
    state: PlaylistUiState,
    onAction: (PlaylistAction) -> Unit = {}
) {
    when (state) {
        PlaylistUiState.Loading -> {
            VibeCircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                color = MaterialTheme.colorScheme.primary
            )
        }

        is PlaylistUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 4.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val playlistCount = state.playlists.size + 1

                    Text(
                        text = pluralStringResource(
                            id = R.plurals.playlist_count,
                            count = playlistCount,
                            playlistCount
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    VibeIconButton(
                        onClick = {
                            onAction(PlaylistAction.OnAddPlaylistClick)
                        }
                    ) {
                        Icon(
                            imageVector = VibeIcons.Filled.Plus,
                            contentDescription = stringResource(R.string.create_playlist)
                        )
                    }
                }

                VibeMediaCard(
                    onClick = {},
                    image = {
                        VibeGradientIcon(
                            icon = VibeIcons.Duotone.Favourite,
                            contentDescription = null,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    },
                    title = stringResource(R.string.favourites),
                    subtitle = pluralStringResource(
                        id = R.plurals.song_count,
                        count = state.favouriteSongs,
                        state.favouriteSongs
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    action = {
                        MenuIconButton(
                            onClick = {}
                        )
                    }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.my_playlists, state.playlists.size),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    top = 16.dp,
                                    bottom = 8.dp
                                )
                        )
                    }

                    if (state.playlists.isEmpty()) {
                        item {
                            VibeOutlinedButton(
                                onClick = {
                                    onAction(PlaylistAction.OnAddPlaylistClick)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 8.dp)
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.Plus,
                                    contentDescription = null
                                )

                                Text(
                                    text = stringResource(R.string.create_playlist),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    } else {
                        items(state.playlists, key = { it.id }) { playlist ->
                            val padding = PaddingValues(horizontal = 16.dp)

                            VibeMediaCard(
                                onClick = {},
                                image = {
                                    SubcomposeAsyncImage(
                                        model = playlist.albumArtUri,
                                        contentDescription = null,
                                        error = {
                                            VibeGradientIcon(
                                                icon = VibeIcons.Duotone.Playlist,
                                                contentDescription = null,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        modifier = Modifier.clip(CircleShape)
                                    )
                                },
                                title = playlist.name,
                                subtitle = pluralStringResource(
                                    id = R.plurals.song_count,
                                    count = playlist.songsCount,
                                    playlist.songsCount
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = padding,
                                action = {
                                    MenuIconButton(
                                        onClick = {}
                                    )
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(padding)
                            )
                        }
                    }
                }
            }

            if (state.isCreatePlaylistSheetOpen) {
                CreatePlaylistSheet(
                    onPlaylistCreated = {
                        onAction(PlaylistAction.OnPlaylistCreated(it))
                    },
                    onDismiss = {
                        onAction(PlaylistAction.OnDismissPlaylistCreateSheet)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlaylistScreenPreview(
    @PreviewParameter(PlaylistPreviewParameterProvider::class) state: PlaylistUiState
) {
    VibePlayerTheme {
        PlaylistScreen(
            state = state
        )
    }
}
