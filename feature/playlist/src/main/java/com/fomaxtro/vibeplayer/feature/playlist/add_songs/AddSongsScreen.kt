package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCheckbox
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeCircularProgressIndicator
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFilledButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeNavigationButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSearchBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.backgroundGradient
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.feature.playlist.R
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Composable
fun AddSongsScreen(
    state: AddSongsState
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                navigationIcon = {
                    VibeNavigationButton(
                        onClick = {}
                    )
                },
                title = stringResource(R.string.add_songs)
            )
        }
    ) { innerPadding ->
        when (state) {
            AddSongsState.Loading -> {
                VibeCircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .padding(innerPadding)
                )
            }

            is AddSongsState.Success -> {
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        stickyHeader {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
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
                                        .clickable {  }
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 14.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    VibeCheckbox(
                                        checked = state.selectAll,
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
                            }
                        }
                        
                        if (state.songs.isNotEmpty()) {
                            items(state.songs, key = { it.song.id }) { selectableSong ->
                                val song = selectableSong.song
                                val padding = PaddingValues(horizontal = 16.dp)

                                VibeSongCard(
                                    onClick = {},
                                    imageUrl = song.albumArtUri,
                                    title = song.title,
                                    artist = song.artist,
                                    duration = song.duration.formatDuration(),
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingContent = {
                                        VibeCheckbox(
                                            checked = selectableSong.selected,
                                            onCheckedChange = {}
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
                                onClick = {},
                                text = "Continue",
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
    state: AddSongsState
) {
    VibePlayerTheme {
        AddSongsScreen(
            state = state
        )
    }
}