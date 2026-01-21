package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibeInnerTopAppBar(
    navigationIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    title: String? = null
) {
    CenterAlignedTopAppBar(
        title = {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier,
        actions = actions
    )
}

@Preview
@Composable
private fun VibeInnerTopAppBarPreview() {
    VibePlayerTheme {
        VibeInnerTopAppBar(
            title = "Scan Music",
            navigationIcon = {
                VibeNavigationButton(
                    onClick = {}
                )
            }
        )
    }
}