package com.fomaxtro.vibeplayer.feature.playlist.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.playlist.R

@Composable
fun MenuIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = VibeIcons.Outlined.Menu,
            contentDescription = stringResource(R.string.menu)
        )
    }
}

@Preview
@Composable
private fun MenuIconButtonPreview() {
    VibePlayerTheme {
        MenuIconButton(
            onClick = {}
        )
    }
}