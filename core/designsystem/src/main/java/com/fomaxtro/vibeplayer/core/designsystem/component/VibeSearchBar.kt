package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeSearchBar(
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    VibeOutlinedTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = VibeIcons.Outlined.Search,
                contentDescription = stringResource(R.string.search)
            )
        },
        placeholder = stringResource(R.string.search),
        trailingIcon = if (state.text.isNotBlank()) {
            @Composable {
                IconButton(
                    onClick = { state.clearText() }
                ) {
                    Icon(
                        imageVector = VibeIcons.Filled.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            }
        } else null,
    )
}

@Preview
@Composable
private fun VibeSearchBarPreview() {
    VibePlayerTheme {
        VibeSearchBar(
            state = TextFieldState()
        )
    }
}