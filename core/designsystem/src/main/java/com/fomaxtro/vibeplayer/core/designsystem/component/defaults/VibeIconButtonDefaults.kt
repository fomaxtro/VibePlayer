package com.fomaxtro.vibeplayer.core.designsystem.component.defaults

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover

object VibeIconButtonDefaults {
    val width = 16.dp
    val height = 16.dp

    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.buttonHover,
        contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ): VibeIconButtonColors {
        return VibeIconButtonColors(
            contentColor = contentColor,
            containerColor = containerColor
        )
    }

    @Composable
    fun IconBox(
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = width,
                    height = height
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

data class VibeIconButtonColors(
    val containerColor: Color,
    val contentColor: Color
)