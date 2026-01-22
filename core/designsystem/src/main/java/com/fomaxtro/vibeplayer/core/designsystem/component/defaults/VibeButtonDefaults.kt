package com.fomaxtro.vibeplayer.core.designsystem.component.defaults

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.textDisabled

data class VibeButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object VibeButtonDefaults {
    val minHeight = 44.dp
    val shape: Shape = CircleShape

    val contentPadding = PaddingValues(
        horizontal = 24.dp,
        vertical = 11.dp
    )

    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = MaterialTheme.colorScheme.buttonHover,
        disabledContentColor: Color = MaterialTheme.colorScheme.textDisabled
    ) = VibeButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}