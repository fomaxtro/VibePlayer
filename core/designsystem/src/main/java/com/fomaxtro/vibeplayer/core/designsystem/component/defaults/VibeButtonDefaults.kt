package com.fomaxtro.vibeplayer.core.designsystem.component.defaults

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline
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
        containerColor: Color = VibeButtonTokens.containerColor,
        contentColor: Color = VibeButtonTokens.contentColor,
        disabledContainerColor: Color = VibeButtonTokens.disabledContainerColor,
        disabledContentColor: Color = VibeButtonTokens.disabledContentColor
    ) = VibeButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonColors(
        contentColor: Color = VibeButtonTokens.contentColor,
        disabledContentColor: Color = VibeButtonTokens.disabledContentColor
    ) = VibeButtonColors(
        containerColor = Color.Transparent,
        contentColor = contentColor,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonBorder() = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.surfaceOutline
    )

    private object VibeButtonTokens {
        val containerColor: Color
            @Composable get() = MaterialTheme.colorScheme.primary
        val contentColor: Color
            @Composable get() = MaterialTheme.colorScheme.onPrimary
        val disabledContainerColor: Color
            @Composable get() = MaterialTheme.colorScheme.buttonHover
        val disabledContentColor: Color
            @Composable get() = MaterialTheme.colorScheme.textDisabled
    }
}