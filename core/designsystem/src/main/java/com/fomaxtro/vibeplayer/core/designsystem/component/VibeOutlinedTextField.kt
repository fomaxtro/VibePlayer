package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.textDisabled

@Composable
fun VibeOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    placeholder: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: VibeTextFieldColors = VibeOutlinedTextFieldDefaults.colors(),
    singleLine: Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Surface(
                shape = shape,
                border = BorderStroke(
                    color = colors.borderColor,
                    width = 1.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    if (leadingIcon != null) {
                        CompositionLocalProvider(
                            LocalContentColor provides colors.leadingIconColor,
                            content = leadingIcon
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        propagateMinConstraints = true
                    ) {
                        if (placeholder != null && value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle,
                                color = colors.placeholderColor
                            )
                        }

                        innerTextField()
                    }

                    if (trailingIcon != null) {
                        CompositionLocalProvider(
                            LocalContentColor provides colors.trailingIconColor,
                            content = trailingIcon
                        )
                    }
                }
            }
        },
        modifier = modifier.height(44.dp),
        textStyle = textStyle.merge(
            color = colors.textColor
        ),
        cursorBrush = SolidColor(colors.textColor),
        singleLine = singleLine
    )
}

data class VibeTextFieldColors(
    val containerColor: Color,
    val textColor: Color,
    val leadingIconColor: Color,
    val trailingIconColor: Color,
    val borderColor: Color,
    val placeholderColor: Color
)

object VibeOutlinedTextFieldDefaults {
    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.buttonHover,
        textColor: Color = MaterialTheme.colorScheme.onPrimary,
        leadingIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        trailingIconColor: Color = MaterialTheme.colorScheme.textDisabled,
        borderColor: Color = MaterialTheme.colorScheme.outline,
        placeholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ) = VibeTextFieldColors(
        containerColor = containerColor,
        textColor = textColor,
        leadingIconColor = leadingIconColor,
        trailingIconColor = trailingIconColor,
        borderColor = borderColor,
        placeholderColor = placeholderColor
    )
}

@Preview
@Composable
private fun VibeOutlinedTextFieldPreview() {
    VibePlayerTheme {
        VibeOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "sdsd",
            onValueChange = {},
            placeholder = "Search",
            leadingIcon = {
                Icon(
                    imageVector = VibeIcons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = VibeIcons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        )
    }
}