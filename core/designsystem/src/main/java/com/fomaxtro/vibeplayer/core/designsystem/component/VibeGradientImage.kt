package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeGradientImage(
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = 64.dp,
                minHeight = 64.dp
            )
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.2f),
                        color.copy(alpha = 0f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides color
        ) {
            Box(
                modifier = Modifier.fillMaxSize(fraction = 0.5625f),
                propagateMinConstraints = true
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun VibeGradientImagePreview() {
    VibePlayerTheme {
        VibeGradientImage(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(128.dp),
            onClick = {}
        ) {
            Icon(
                imageVector = VibeIcons.Outlined.Music,
                contentDescription = null
            )
        }
    }
}