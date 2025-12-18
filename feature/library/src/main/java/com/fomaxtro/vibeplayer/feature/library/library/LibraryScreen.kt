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
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.library.component.LibraryTopBar
import com.fomaxtro.vibeplayer.feature.library.library.component.PlaybackControls
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreen(
    onSongClick: (songIndex: Int) -> Unit,
    onScanMusic: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LibraryAction.OnSongClick -> onSongClick(action.songIndex)
                is LibraryAction.OnScanMusicClick -> onScanMusic()
                else -> viewModel.onAction(action)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun LibraryScreen(
    state: LibraryUiState,
    modifier: Modifier = Modifier,
    onAction: (LibraryAction) -> Unit = {}
) {
    val songsListState = rememberLazyListState()
    val isShowingScrollUp by remember {
        derivedStateOf {
            songsListState.firstVisibleItemIndex > 10
        }
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            LibraryTopBar(
                searchQuery = state.query,
                onSearchQueryChange = {
                    onAction(LibraryAction.OnSearchQueryChange(it))
                },
                onScanMusicClick = {
                    onAction(LibraryAction.OnScanMusicClick)
                },
                onSearchClick = {
                    onAction(LibraryAction.OnSearchClick)
                },
                onClearClick = {
                    onAction(LibraryAction.OnClearClick)
                },
                onCancelClick = {
                    onAction(LibraryAction.OnCancelClick)
                },
                search = state.isSearching
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
        },
        modifier = modifier
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

                items(state.songs.size) { index ->
                    val song = state.songs[index]

                    VibeSongCard(
                        onClick = {
                            onAction(LibraryAction.OnSongClick(index))
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
                )
            )
        }
    }
}