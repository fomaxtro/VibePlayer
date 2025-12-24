package com.fomaxtro.vibeplayer.feature.library.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedTextField
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.Resource
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.library.R
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun SearchScreen(
    onCancel: () -> Unit,
    onPlaySong: () -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            SearchEvent.PlaySong -> onPlaySong()
        }
    }

    SearchScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SearchAction.OnCancelClick -> onCancel()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    state: SearchUiState,
    onAction: (SearchAction) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        searchFocusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    VibeOutlinedTextField(
                        state = state.searchQuery,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(searchFocusRequester),
                        leadingIcon = {
                            Icon(
                                imageVector = VibeIcons.Outlined.Search,
                                contentDescription = stringResource(R.string.search)
                            )
                        },
                        placeholder = stringResource(R.string.search),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    onAction(SearchAction.OnClearClick)
                                }
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.Close,
                                    contentDescription = stringResource(R.string.close)
                                )
                            }
                        },
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            keyboardController?.hide()
                            onAction(SearchAction.OnCancelClick)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            )
        },
        modifier = Modifier.imePadding()
    ) { innerPadding ->
        Crossfade(
            targetState = state.songs,
            modifier = Modifier.padding(innerPadding)
        ) { songs ->
            when (songs) {
                Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                is Resource.Success -> {
                    if (songs.data.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_results_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(songs.data) { song ->
                                VibeSongCard(
                                    onClick = {
                                        keyboardController?.hide()
                                        onAction(SearchAction.OnSongClick(song))
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
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    val searchQuery = rememberTextFieldState()
    val songs = listOf(
        Song(
            id = 1,
            title = "Test",
            artist = "Test",
            duration = 3.minutes,
            filePath = "",
            sizeBytes = 1024L,
            albumArtUri = ""
        )
    )

    VibePlayerTheme {
        SearchScreen(
            state = SearchUiState(
                searchQuery = searchQuery,
                songs = Resource.Loading
            )
        )
    }
}