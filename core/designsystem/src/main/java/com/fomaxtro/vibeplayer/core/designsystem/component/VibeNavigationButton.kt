package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeNavigationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    VibeIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = VibeIcons.Filled.ArrowLeft,
            contentDescription = stringResource(R.string.navigate_back)
        )
    }
}

@Preview
@Composable
private fun VibeNavigationButtonPreview() {
    VibePlayerTheme {
        VibeNavigationButton()
    }
}