package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeAppTitle(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun VibeAppTitlePreview() {
    VibePlayerTheme {
        VibeAppTitle()
    }
}