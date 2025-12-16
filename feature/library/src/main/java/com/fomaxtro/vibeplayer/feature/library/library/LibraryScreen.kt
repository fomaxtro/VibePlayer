package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFloatingActionButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIconButton
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
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

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
            }
        },
        modifier = modifier
    )
}

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
            VibeMainTopAppBar(
                actions = {
                    VibeScanIconButton(
                        onClick = {
                            onAction(LibraryAction.OnScanMusicClick)
                        }
                    )
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
        },
        modifier = modifier
    ) { innerPadding ->
        when (state) {
            LibraryUiState.Loading -> Unit

            is LibraryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    state = songsListState
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                VibeOutlinedButton(
                                    onClick = {},
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = VibeIcons.Outlined.Repeat,
                                        contentDescription = stringResource(R.string.shuffle)
                                    )

                                    Text(
                                        text = stringResource(R.string.shuffle)
                                    )
                                }

                                VibeOutlinedButton(
                                    onClick = {},
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = VibeIcons.Outlined.Play,
                                        contentDescription = stringResource(DesignR.string.play)
                                    )

                                    Text(
                                        text = stringResource(DesignR.string.play)
                                    )
                                }
                            }

                            Text(
                                text = pluralStringResource(
                                    id = R.plurals.songs_available,
                                    count = state.songs.size,
                                    state.songs.size
                                ),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
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
}

@DevicePreviews
@Composable
private fun ScanMusicScreenPreview() {
    val songs = (1..3).map {
        Song(
            id = it.toLong(),
            title = "Music Title",
            artist = "Artist",
            duration = 3.minutes,
            filePath = "",
            sizeBytes = 1024 * 5,
            albumArtUri = ""
        )
    }
    
    VibePlayerTheme {
        Surface {
            LibraryScreen(
                state = LibraryUiState.Success(
                    songs = songs
                )
            )
        }
    }
}