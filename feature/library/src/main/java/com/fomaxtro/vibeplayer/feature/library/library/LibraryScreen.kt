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
import androidx.compose.foundation.lazy.items
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
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.feature.library.component.ScanIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.feature.library.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun LibraryScreen(
    autoScan: Boolean,
    onScanMusic: () -> Unit,
    viewModel: LibraryViewModel = koinViewModel {
        parametersOf(autoScan)
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LibraryAction.OnScanMusicClick -> onScanMusic()
                else -> viewModel.onAction(action)
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
        topBar = {
            VibeMainTopAppBar(
                actions = {
                    VibeIconButton(
                        onClick = {
                            onAction(LibraryAction.OnScanMusicClick)
                        }
                    ) {
                        Icon(
                            imageVector = VibeIcons.Scan,
                            contentDescription = stringResource(R.string.scan)
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
            when (state) {
                LibraryUiState.Loading -> {
                    ScanIndicator(
                        scanning = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.scanning_music),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LibraryUiState.Empty -> {
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

                is LibraryUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = songsListState
                    ) {
                        items(state.songs, key = { it.id }) { song ->
                            VibeSongCard(
                                onClick = {},
                                title = song.title,
                                artist = song.artist,
                                duration = song.durationMillis.formatDuration(),
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
                state = LibraryUiState.Loading
            )
        }
    }
}