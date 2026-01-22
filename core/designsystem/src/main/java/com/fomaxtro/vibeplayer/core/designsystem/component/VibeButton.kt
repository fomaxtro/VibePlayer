package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeButtonColors
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeButtonDefaults
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover

@Composable
fun VibeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = VibeButtonDefaults.shape,
    enabled: Boolean = true,
    colors: VibeButtonColors = VibeButtonDefaults.colors(),
    loading: Boolean = false,
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = VibeButtonDefaults.minHeight)
            .clip(shape)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null
            ),
        shape = shape,
        color = if (enabled) colors.containerColor else colors.disabledContainerColor,
        contentColor = if (enabled) colors.contentColor else colors.disabledContentColor,
        border = border
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelLarge
        ) {
            Row(
                modifier = Modifier.padding(VibeButtonDefaults.contentPadding),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (loading) {
                    VibeCircularProgressIndicator(
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
private fun VibeButtonPreview() {
    VibePlayerTheme {
        VibeButton(
            onClick = {}
        ) {
            Text("Button")
        }
    }
}