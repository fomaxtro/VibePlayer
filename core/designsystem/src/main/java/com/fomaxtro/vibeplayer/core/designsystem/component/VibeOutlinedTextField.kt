package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonHover
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline

@Composable
fun VibeOutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    placeholder: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null
) {
    OutlinedTextField(
        state = state,
        modifier = modifier,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.buttonHover,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceOutline
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = placeholder?.let { placeholder ->
            @Composable {
                Text(
                    text = placeholder,
                    style = textStyle
                )
            }
        },
        textStyle = textStyle.merge(
            color = MaterialTheme.colorScheme.onPrimary
        ),
        lineLimits = lineLimits,
        suffix = suffix,
        inputTransformation = inputTransformation,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction
    )
}

@Preview
@Composable
private fun VibeOutlinedTextFieldPreview() {
    VibePlayerTheme {
        VibeOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = TextFieldState(),
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