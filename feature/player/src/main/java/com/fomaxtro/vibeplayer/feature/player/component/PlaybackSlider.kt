package com.fomaxtro.vibeplayer.feature.player.component

import android.graphics.RectF
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceBg30
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun PlaybackSlider(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceOutline,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressText: (() -> String)? = null,
    progressTextPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    progressTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    cornerRadius: CornerRadius = CornerRadius(100f, 100f)
) {
    val textMeasurer = rememberTextMeasurer()
    val progressTextResult = progressText?.let { progressText ->
        textMeasurer.measure(
            text = progressText(),
            style = progressTextStyle
        )
    }
    val progressTextResultDp = with(LocalDensity.current) {
        progressTextResult?.size?.height?.toDp()
    }

    val shadowColor = MaterialTheme.colorScheme.surfaceBg30
    val shadowRadiusPx = with(LocalDensity.current) {
        4.dp.toPx()
    }

    Canvas(
        modifier = modifier
            .heightIn(min = 4.dp)
            .defaultMinSize(minWidth = 4.dp)
            .then(
                if (progressTextResultDp != null) {
                    Modifier.height(progressTextResultDp)
                } else Modifier
            )
    ) {
        val progress = size.width * value

        if (progressText != null && progressTextResult != null) {
            val progressHeight = size.height / 2
            val centerOffset = Offset(
                x = 0f,
                y = size.center.y / 2
            )

            drawRoundRect(
                color = containerColor,
                cornerRadius = cornerRadius,
                size = Size(
                    width = size.width,
                    height = progressHeight
                ),
                topLeft = centerOffset
            )

            drawRoundRect(
                color = contentColor,
                cornerRadius = cornerRadius,
                size = Size(
                    width = progress,
                    height = progressHeight
                ),
                topLeft = centerOffset
            )

            val leftPadding = progressTextPadding
                .calculateLeftPadding(layoutDirection)
                .toPx()
            val rightPadding = progressTextPadding
                .calculateRightPadding(layoutDirection)
                .toPx()

            val progressTextContainerSize = Size(
                width = progressTextResult.size.width + leftPadding + rightPadding,
                height = size.height
            )
            val progressTextContainerOffset = Offset(
                x = progress - progressTextContainerSize.width / 2,
                y = 0f
            )

            val progressContainerShadow = Paint().asFrameworkPaint().apply {
                color = contentColor.toArgb()

                setShadowLayer(
                    shadowRadiusPx,
                    0f,
                    0f,
                    shadowColor.toArgb()
                )
            }

            drawIntoCanvas {
                it.nativeCanvas.drawRoundRect(
                    RectF(
                        progressTextContainerOffset.x,
                        progressTextContainerOffset.y,
                        progressTextContainerOffset.x + progressTextContainerSize.width,
                        progressTextContainerOffset.y + progressTextContainerSize.height
                    ),
                    cornerRadius.x,
                    cornerRadius.y,
                    progressContainerShadow
                )
            }

            drawText(
                textLayoutResult = progressTextResult,
                topLeft = Offset(
                    x = progressTextContainerOffset.x + leftPadding,
                    y = 0f
                )
            )
        } else {
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
}

@Preview
@Composable
private fun PlaybackSliderPreview() {
    val totalPlayback = 3.minutes
    val currentPlayback = 90.seconds

    VibePlayerTheme {
        PlaybackSlider(
            modifier = Modifier.fillMaxWidth(),
            value = 0.2f,
            progressText = {
                val currentPlaybackText = currentPlayback.toComponents { minutes, seconds, _ ->
                    "${minutes.toString().padStart(1, '0')}:${seconds.toString().padStart(2, '0')}"
                }
                val totalPlaybackText = totalPlayback.toComponents { minutes, seconds, _ ->
                    "${minutes.toString().padStart(1, '0')}:${seconds.toString().padStart(2, '0')}"
                }

                "$currentPlaybackText / $totalPlaybackText"
            }
        )
    }
}