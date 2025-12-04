package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibeMainTopAppBar(
    actions: @Composable RowScope.() -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        actions = actions,
        modifier = modifier
    )
}

@Preview
@Composable
private fun VibeMainTopAppBarPreview() {
    VibePlayerTheme {
        VibeMainTopAppBar(
            text = "VibePlayer",
            actions = {}
        )
    }
}