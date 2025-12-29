package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFloatingActionButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.library.component.LibraryLayoyt
import com.fomaxtro.vibeplayer.feature.library.library.component.PlaybackControls
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@Composable
fun LibraryScreen(
    onScanMusic: () -> Unit,
    onSearch: () -> Unit,
    songsListState: LazyListState,
    viewModel: LibraryViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LibraryAction.OnScanMusicClick -> onScanMusic()
                LibraryAction.OnSearchClick -> onSearch()
                else -> viewModel.onAction(action)
            }
        },
        songsListState = songsListState
    )
}

@Composable
private fun LibraryScreen(
    state: LibraryUiState,
    onAction: (LibraryAction) -> Unit = {},
    songsListState: LazyListState
) {
    val isShowingScrollUp by remember {
        derivedStateOf {
            songsListState.firstVisibleItemIndex > 10
        }
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            VibeMainTopAppBar(
                actions = {
                    VibeScanIconButton(
                        onClick = {
                            onAction(LibraryAction.OnScanMusicClick)
                        }
                    )

                    VibeIconButton(
                        onClick = {
                            onAction(LibraryAction.OnSearchClick)
                        }
                    ) {
                        Icon(
                            imageVector = VibeIcons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            val animationSpec = spring<Float>(
                stiffness = Spring.StiffnessMedium
            )

            AnimatedVisibility(
                visible = isShowingScrollUp,
                enter = scaleIn(
                    animationSpec = animationSpec
                ),
                exit = scaleOut(
                    animationSpec = animationSpec
                )
            ) {
                VibeFloatingActionButton(
                    onClick = {
                        scope.launch {
                            songsListState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(
                        imageVector = VibeIcons.Filled.ArrowUp,
                        contentDescription = stringResource(R.string.scroll_up)
                    )
                }
            }
        }
    ) { innerPadding ->
        LibraryLayoyt(
            playbackControls = {
                PlaybackControls(
                    onShuffleClick = {
                        onAction(LibraryAction.OnShuffleClick)
                    },
                    onPlayClick = {
                        onAction(LibraryAction.OnPlayClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            songs = state.songs,
            item = { song, contentPadding ->
                VibeSongCard(
                    onClick = {
                        onAction(LibraryAction.OnSongClick(song))
                    },
                    title = song.title,
                    artist = song.artist,
                    duration = song.duration.formatDuration(),
                    image = {
                        SubcomposeAsyncImage(
                            model = song.albumArtUri,
                            contentDescription = null,
                            error = {
                                VibeSongDefaultImage()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    contentPadding = contentPadding
                )
            },
            songsCount = {
                Text(
                    text = pluralStringResource(
                        id = R.plurals.songs_available,
                        count = state.songs.size,
                        state.songs.size
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = songsListState
        )
    }
}

@DevicePreviews
@Composable
private fun ScanMusicScreenPreview() {
    VibePlayerTheme {
        Surface {
            LibraryScreen(
                state = LibraryUiState(
                    songs = listOf(
                        Song(
                            id = 1,
                            title = "Song 1",
                            artist = "Artist 1",
                            duration = 3.minutes,
                            albumArtUri = null,
                            filePath = "",
                            sizeBytes = 1024L,
                        )
                    )
                ),
                songsListState = rememberLazyListState()
            )
        }
    }
}