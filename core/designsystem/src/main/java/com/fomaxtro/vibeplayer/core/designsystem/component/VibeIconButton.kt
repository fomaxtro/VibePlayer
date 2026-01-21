package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeIconButtonColors
import com.fomaxtro.vibeplayer.core.designsystem.component.defaults.VibeIconButtonDefaults
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

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
        VibeIconButtonDefaults.IconBox(
            content = content
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