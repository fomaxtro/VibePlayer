package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = 2.dp
    )
}

@Preview
@Composable
private fun VibeCircularProgressIndicatorPreview() {
    VibePlayerTheme {
        VibeCircularProgressIndicator()
    }
}