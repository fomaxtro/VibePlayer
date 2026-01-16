package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline

@Composable
fun VibeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .border(
                color = MaterialTheme.colorScheme.surfaceOutline,
                width = 1.dp,
                shape = shape
            )
            .clip(shape)
            .clickable(
                onClick = onClick,
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelLarge,
            LocalContentColor provides MaterialTheme.colorScheme.onPrimary
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 11.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = content
            )
        }
    }
}

@Preview
@Composable
private fun VibeOutlinedButtonPreview() {
    VibePlayerTheme {
        VibeOutlinedButton(
            onClick = {}
        ) {
            Text("Button")
        }
    }
}