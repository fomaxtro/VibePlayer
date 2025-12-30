package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibeMainTopAppBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { VibeAppTitle() },
        actions = actions,
        modifier = modifier
    )
}

@Preview
@Composable
private fun VibeMainTopAppBarPreview() {
    VibePlayerTheme {
        VibeMainTopAppBar(
            actions = {}
        )
    }
}