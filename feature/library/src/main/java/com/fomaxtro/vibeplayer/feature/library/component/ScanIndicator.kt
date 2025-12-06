package com.fomaxtro.vibeplayer.feature.library.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeImages
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun ScanIndicator(
    scanning: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation by if (scanning) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000)
            )
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    Image(
        painter = VibeImages.Radar,
        contentDescription = null,
        modifier = modifier
            .size(140.dp)
            .graphicsLayer {
                rotationZ = rotationAnimation
            }
    )
}

@Preview
@Composable
private fun ScanIndicatorPreview() {
    VibePlayerTheme {
        ScanIndicator(
            scanning = false
        )
    }
}