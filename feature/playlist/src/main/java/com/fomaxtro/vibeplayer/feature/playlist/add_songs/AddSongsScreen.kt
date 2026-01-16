package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCheckbox
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCircularProgressIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFilledButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeNavigationButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSearchBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.backgroundGradient
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.feature.playlist.R
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Composable
fun AddSongsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddSongsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            AddSongsEvent.PlaylistCreated -> onNavigateBack()
        }
    }

    AddSongsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                AddSongsAction.OnNavigateBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
internal fun AddSongsScreen(
    state: AddSongsUiState,
    onAction: (AddSongsAction) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                navigationIcon = {
                    VibeNavigationButton(
                        onClick = {
                            onAction(AddSongsAction.OnNavigateBackClick)
                        }
                    )
                },
                title = if (state is AddSongsUiState.Success && state.selectedSongsCount > 0) {
                    stringResource(
                        id = R.string.selected,
                        state.selectedSongsCount
                    )
                } else {
                    stringResource(R.string.add_songs)
                }
            )
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
            .imePadding()
    ) { innerPadding ->
        when (state) {
            AddSongsUiState.Loading -> {
                VibeCircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .padding(innerPadding)
                )
            }

            is AddSongsUiState.Success -> {
                Box(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        VibeSearchBar(
                            state = state.search,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAction(AddSongsAction.OnSelectAllToggle)
                                }
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 14.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            VibeCheckbox(
                                checked = state.isSelectedAll,
                                onCheckedChange = {}
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = stringResource(R.string.select_all),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            if (state.songs.isNotEmpty()) {
                                items(state.songs, key = { it.song.id }) { selectableSong ->
                                    val song = selectableSong.song
                                    val padding = PaddingValues(horizontal = 16.dp)

                                    VibeSongCard(
                                        onClick = {
                                            onAction(AddSongsAction.OnSongClick(selectableSong))
                                        },
                                        imageUrl = song.albumArtUri,
                                        title = song.title,
                                        artist = song.artist,
                                        duration = song.duration.formatDuration(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateItem(),
                                        leadingContent = {
                                            VibeCheckbox(
                                                checked = selectableSong.selected,
                                                onCheckedChange = {
                                                    onAction(AddSongsAction.OnSongClick(selectableSong))
                                                }
                                            )
                                        },
                                        contentPadding = padding
                                    )

                                    HorizontalDivider(
                                        modifier = Modifier.padding(padding)
                                    )
                                }

                                if (state.canSubmit) {
                                    item {
                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                        )
                                    }
                                }
                            } else {
                                item {
                                    Text(
                                        text = stringResource(DesignR.string.no_results_found),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth()
                                            .padding(16.dp),

                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(MaterialTheme.colorScheme.backgroundGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.canSubmit) {
                            VibeFilledButton(
                                onClick = {
                                    onAction(AddSongsAction.OnSubmitClick)
                                },
                                text = stringResource(DesignR.string.ok),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
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
private fun AddSongsScreenPreview(
    @PreviewParameter(AddSongsPreviewParameterProvider::class)
    state: AddSongsUiState
) {
    VibePlayerTheme {
        AddSongsScreen(
            state = state
        )
    }
}