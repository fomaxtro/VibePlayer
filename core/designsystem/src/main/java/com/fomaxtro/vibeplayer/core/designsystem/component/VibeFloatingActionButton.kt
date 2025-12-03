package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.icon.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover

@Composable
fun VibeFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    color: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(
                onClick = onClick,
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null
            ),
        shape = shape,
        color = color
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun VibeFloatingActionButtonPreview() {
    VibePlayerTheme {
        VibeFloatingActionButton(
            onClick = {}
        ) {
            Icon(
                imageVector = VibeIcons.Play,
                contentDescription = null
            )
        }
    }
}