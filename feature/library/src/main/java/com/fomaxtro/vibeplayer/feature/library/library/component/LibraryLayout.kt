package com.fomaxtro.vibeplayer.feature.library.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration.Companion.minutes

@Composable
fun LibraryLayout(
    playbackControls: @Composable () -> Unit,
    item: @Composable LazyItemScope.(item: Song, contentPadding: PaddingValues) -> Unit,
    songsCount: @Composable () -> Unit,
    songs: List<Song>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    if (isWideScreen) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.width(IntrinsicSize.Max)
                ) {
                    playbackControls()
                }

                songsCount()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = state,
            ) {
                items(songs, key = { it.id }) {
                    item(it, PaddingValues(horizontal = 24.dp))
                }
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            state = state
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    playbackControls()
                    songsCount()
                }
            }

            items(songs, key = { it.id }) {
                item(it, PaddingValues(horizontal = 16.dp))
            }
        }
    }
}

@DevicePreviews
@Composable
private fun LibraryLayoutPreview() {
    LibraryLayout(
        playbackControls = {
            PlaybackControls(
                onShuffleClick = {},
                onPlayClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        },
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
        ),
        item = { song, contentPadding ->
            SongCard(
                onClick = {},
                title = song.title,
                artist = song.artist,
                duration = song.duration.formatDuration(),
                imageUrl = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                contentPadding = contentPadding
            )
        },
        songsCount = {
            Text(text = "2 songs")
        },
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState()
    )
}