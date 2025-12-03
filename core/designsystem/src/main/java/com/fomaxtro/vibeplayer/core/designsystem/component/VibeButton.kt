package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.extension.vibeShadow
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover

@Composable
fun VibeButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    colors: VibeButtonColors = VibeButtonDefaults.colors(),
    enabled: Boolean = true
) {
    Surface(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .clip(shape)
            .clickable(
                onClick = onClick,
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null,
                enabled = enabled
            )
            .vibeShadow(shape),
        color = if (enabled) colors.containerColor else colors.disabledContainerColor,
        shape = shape
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 11.dp
                ),
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) colors.contentColor else colors.disabledContentColor
        )
    }
}

data class VibeButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object VibeButtonDefaults {
    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = MaterialTheme.colorScheme.buttonHover,
        disabledContentColor: Color = MaterialTheme.colorScheme.outline
    ) = VibeButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}

@Preview
@Composable
private fun VibeButtonPreview() {
    VibePlayerTheme {
        VibeButton(
            onClick = {},
            text = "Button"
        )
    }
}