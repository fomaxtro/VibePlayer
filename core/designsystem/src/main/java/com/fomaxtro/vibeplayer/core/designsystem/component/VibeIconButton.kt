package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover

@Composable
fun VibeIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: VibeIconButtonColors = VibeIconButtonDefaults.colors(),
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor
        ),
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 16.dp,
                    minHeight = 16.dp
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

object VibeIconButtonDefaults {
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
}

@Preview
@Composable
private fun VibeIconButtonPreview() {
    VibePlayerTheme {
        VibeIconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = VibeIcons.Outlined.Scan,
                contentDescription = null
            )
        }
    }
}