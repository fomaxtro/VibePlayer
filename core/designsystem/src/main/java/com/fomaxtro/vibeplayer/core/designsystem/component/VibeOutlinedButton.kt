package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeButtonColors
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeButtonDefaults
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = VibeButtonDefaults.shape,
    enabled: Boolean = true,
    colors: VibeButtonColors = VibeButtonDefaults.outlinedButtonColors(),
    loading: Boolean = false,
    border: BorderStroke = VibeButtonDefaults.outlinedButtonBorder(),
    content: @Composable RowScope.() -> Unit
) {
    VibeButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        enabled = enabled,
        colors = colors,
        loading = loading,
        content = content,
        border = border
    )
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