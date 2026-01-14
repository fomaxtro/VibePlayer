package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.textDisabled

@Composable
fun VibeCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: VibeCheckboxColors = VibeCheckboxDefaults.colors(),
    shape: Shape = VibeCheckboxDefaults.shape,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .size(28.dp)
            .clip(shape)
            .clickable(
                onClick = { onCheckedChange(!checked) },
                indication = ripple(
                    color = MaterialTheme.colorScheme.buttonHover
                ),
                interactionSource = null,
                enabled = enabled
            )
            .padding(6.dp)
            .then(
                if (!checked) {
                    Modifier.border(
                        width = 1.dp,
                        color = if (enabled) {
                            colors.uncheckedBorderColor
                        } else {
                            colors.disabledUncheckedBorderColor
                        },
                        shape = shape
                    )
                } else Modifier
            )
            .background(
                color = when {
                    enabled && checked -> colors.checkedContainerColor
                    !enabled && checked -> colors.disabledCheckedContainerColor
                    else -> Color.Transparent
                },
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            val (iconTint, iconBlendMode) = if (enabled) {
                colors.checkedContentColor to BlendMode.SrcOver
            } else {
                Color.Black to BlendMode.DstOut
            }

            Icon(
                imageVector = VibeIcons.Filled.Check,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.graphicsLayer {
                    blendMode = iconBlendMode
                }
            )
        }
    }
}

data class VibeCheckboxColors(
    val checkedContainerColor: Color,
    val checkedContentColor: Color,
    val uncheckedBorderColor: Color,
    val disabledCheckedContainerColor: Color,
    val disabledUncheckedBorderColor: Color,
)

object VibeCheckboxDefaults {
    val shape: Shape = CircleShape

    @Composable
    fun colors() = VibeCheckboxColors(
        uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        checkedContainerColor = MaterialTheme.colorScheme.primary,
        checkedContentColor = MaterialTheme.colorScheme.onPrimary,
        disabledCheckedContainerColor = MaterialTheme.colorScheme.textDisabled,
        disabledUncheckedBorderColor = MaterialTheme.colorScheme.textDisabled
    )
}

@Preview
@Composable
private fun VibeCheckboxPreview() {
    VibePlayerTheme {
        VibeCheckbox(
            checked = false,
            onCheckedChange = {}
        )
    }
}