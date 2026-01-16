package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.textDisabled

@Composable
fun VibeFilledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    colors: VibeButtonColors = VibeButtonDefaults.colors(),
    enabled: Boolean = true,
    loading: Boolean = false
) {
    val enabled = enabled && !loading

    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(
                onClick = onClick,
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null,
                enabled = enabled
            )
            .width(IntrinsicSize.Max),
        color = if (enabled) colors.containerColor else colors.disabledContainerColor,
        contentColor = if (enabled) {
            colors.contentColor
        } else {
            colors.disabledContentColor
        },
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 11.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                VibeCircularProgressIndicator(
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
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
        disabledContentColor: Color = MaterialTheme.colorScheme.textDisabled
    ) = VibeButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}

@Preview
@Composable
private fun VibeFilledButtonPreview() {
    VibePlayerTheme {
        VibeFilledButton(
            onClick = {},
            text = "Button",
            loading = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}