package com.fomaxtro.vibeplayer.feature.scanner.scan_music

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeImages
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.DevicePreviews
import com.fomaxtro.vibeplayer.feature.scanner.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ScanMusicScreen(
    minDurationSeconds: Long,
    minSize: Long,
    viewModel: ScanMusicViewModel = koinViewModel {
        parametersOf(minDurationSeconds, minSize)
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScanMusicScreen(
        state = state
    )
}

@Composable
internal fun ScanMusicScreen(
    state: ScanMusicState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            ScanMusicState.Loading -> {
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

            ScanMusicState.Empty -> {
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
                    onClick = {},
                    text = stringResource(R.string.scan_again)
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ScanMusicScreenPreview() {
    VibePlayerTheme {
        Surface {
            ScanMusicScreen(
                state = ScanMusicState.Empty
            )
        }
    }
}