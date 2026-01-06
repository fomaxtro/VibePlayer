package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
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
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeFloatingActionButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.preview.SongPreviewFactory
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.library.component.LibraryLayout
import com.fomaxtro.vibeplayer.feature.library.library.component.PlaybackControls
import com.fomaxtro.vibeplayer.feature.library.library.component.SongCard
import kotlinx.coroutines.launch

@Composable
fun LibraryScreen(
    onSongClick: (Song) -> Unit,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit,
    songs: List<Song>
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
                        imageVector = VibeIcons.Filled.ArrowUp,
                        contentDescription = stringResource(R.string.scroll_up)
                    )
                }
            }
        }
    ) { innerPadding ->
        LibraryLayout(
            playbackControls = {
                PlaybackControls(
                    onShuffleClick = onShuffleClick,
                    onPlayClick = onPlayClick,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            songs = songs,
            item = { song, contentPadding ->
                SongCard(
                    onClick = {
                        onSongClick(song)
                    },
                    title = song.title,
                    artist = song.artist,
                    duration = song.duration.formatDuration(),
                    imageUrl = song.albumArtUri,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    contentPadding = contentPadding
                )

                HorizontalDivider(
                    modifier = Modifier.padding(contentPadding)
                )
            },
            songsCount = {
                Text(
                    text = pluralStringResource(
                        id = R.plurals.songs_available,
                        count = songs.size,
                        songs.size
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp),
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
                songs = SongPreviewFactory.defaultList,
                onSongClick = {  },
                onPlayClick = {  },
                onShuffleClick = {  },
            )
        }
    }
}