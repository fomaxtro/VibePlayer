package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCircularProgressIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeGradientIcon
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMediaCard
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.playlist.R
import com.fomaxtro.vibeplayer.feature.playlist.component.MenuIconButton
import com.fomaxtro.vibeplayer.feature.playlist.component.PlaylistOutlinedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    state: PlaylistUiState
) {
    Scaffold { innerPadding ->
        when (state) {
            PlaylistUiState.Loading -> {
                VibeCircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .wrapContentSize(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is PlaylistUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    item {
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
                                style = MaterialTheme.typography.labelLarge
                            )

                            VibeIconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.Plus,
                                    contentDescription = stringResource(R.string.create_playlist)
                                )
                            }
                        }
                    }

                    item {
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
                                count = 2,
                                2
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            action = {
                                MenuIconButton(
                                    onClick = {}
                                )
                            }
                        )
                    }
                    
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
                            PlaylistOutlinedButton(
                                onClick = {},
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
                        items(state.playlists) { playlist ->
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
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CircleShape
                                            )
                                        }
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
