package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.library.component.PlaybackControls
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreen(
    onSongClick: (songIndex: Int) -> Unit,
    onScanMusic: () -> Unit,
    onSearchClick: () -> Unit,
    songsListState: LazyListState,
    viewModel: LibraryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LibraryEvent.PlaySong -> onSongClick(event.songIndex)
        }
    }

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LibraryAction.OnScanMusicClick -> onScanMusic()
                LibraryAction.OnSearchClick -> onSearchClick()
                else -> viewModel.onAction(action)
            }
        },
        songsListState = songsListState
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
        if (state.isEmptyQuery) {
            Text(
                text = stringResource(R.string.no_results_found),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                state = songsListState
            ) {
                if (!state.isSearching) {
                    item {
                        PlaybackControls(
                            onShuffleClick = {
                                onAction(LibraryAction.OnShuffleClick)
                            },
                            onPlayClick = {
                                onAction(LibraryAction.OnPlayClick)
                            },
                            songsCount = state.songs.size,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                items(state.songs) { song ->
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
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ScanMusicScreenPreview() {
    VibePlayerTheme {
        Surface {
            LibraryScreen(
                state = LibraryUiState(
                    query = "Test",
                    isSearching = true
                ),
                songsListState = rememberLazyListState()
            )
        }
    }
}