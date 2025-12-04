package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibeInnerTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        navigationIcon = {
            VibeNavigationButton(
                onClick = {}
            )
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun VibeInnerTopAppBarPreview() {
    VibePlayerTheme {
        VibeInnerTopAppBar(
            title = "Scan Music"
        )
    }
}