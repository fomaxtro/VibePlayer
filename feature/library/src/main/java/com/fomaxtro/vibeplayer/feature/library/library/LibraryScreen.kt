package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFloatingActionButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.library.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun LibraryScreen(
    onSongClick: (songIndex: Int) -> Unit,
    onScanAgain: () -> Unit,
    viewModel: LibraryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LibraryAction.OnSongClick -> onSongClick(action.songIndex)
                is LibraryAction.OnScanAgainClick -> onScanAgain()
            }
        }
    )
}

@Composable
private fun LibraryScreen(
    state: LibraryUiState,
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
                        imageVector = VibeIcons.ArrowUp,
                        contentDescription = stringResource(R.string.scroll_up)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state is LibraryUiState.Success) {
                if (state.songs.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = songsListState
                    ) {
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
                                    .animateItem()
                            )
                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.no_music_found),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.no_music_found_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    VibeButton(
                        onClick = {
                            onAction(LibraryAction.OnScanAgainClick)
                        },
                        text = stringResource(R.string.scan_again)
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
                state = LibraryUiState.Success(
                    songs = listOf(
                        Song(
                            id = 0,
                            title = "505",
                            artist = "Arctic Monkeys",
                            duration = 3.minutes,
                            filePath = "",
                            sizeBytes = 500,
                            albumArtUri = ""
                        )
                    )
                )
            )
        }
    }
}