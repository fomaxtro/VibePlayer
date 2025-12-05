package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongCard
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeSongDefaultImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeImages
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.DevicePreviews
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.preview.LibraryPreviewData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun LibraryScreen(
    autoScan: Boolean,
    viewModel: LibraryViewModel = koinViewModel {
        parametersOf(autoScan)
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LibraryScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun LibraryScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            VibeMainTopAppBar(
                actions = {
                    VibeIconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = VibeIcons.Scan,
                            contentDescription = null
                        )
                    }
                }
            )
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
                LibraryState.Loading -> {
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotationAnimation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000)
                        )
                    )

                    Image(
                        painter = VibeImages.Radar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .graphicsLayer {
                                rotationZ = rotationAnimation
                            }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.scanning_music),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LibraryState.Empty -> {
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

                is LibraryState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(state.songs) { song ->
                            VibeSongCard(
                                onClick = {},
                                title = song.title,
                                artist = song.artist,
                                duration = song.duration,
                                image = {
                                    SubcomposeAsyncImage(
                                        model = song.albumArtUri,
                                        contentDescription = null,
                                        error = {
                                            VibeSongDefaultImage()
                                        }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
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
                state = LibraryState.Success(
                    songs = LibraryPreviewData.songs
                )
            )
        }
    }
}