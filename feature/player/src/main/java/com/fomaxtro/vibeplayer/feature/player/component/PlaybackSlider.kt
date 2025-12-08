package com.fomaxtro.vibeplayer.feature.player.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline

@Composable
fun PlaybackSlider(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceOutline,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Canvas(
        modifier = modifier
            .height(6.dp)
    ) {
        val cornerRadius = CornerRadius(100f, 100f)
        val progress = size.width * value

        drawRoundRect(
            color = containerColor,
            cornerRadius = cornerRadius
        )

        drawRoundRect(
            color = contentColor,
            cornerRadius = cornerRadius,
            size = Size(
                width = progress,
                height = size.height
            )
        )
    }
}

@Preview
@Composable
private fun PlaybackSliderPreview() {
    VibePlayerTheme {
        PlaybackSlider(
            modifier = Modifier.fillMaxWidth(),
            value = 0.2f
        )
    }
}