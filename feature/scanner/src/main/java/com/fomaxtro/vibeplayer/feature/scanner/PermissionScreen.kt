package com.fomaxtro.vibeplayer.feature.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.icon.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.DevicePreviews

@Composable
internal fun PermissionScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .then(
                    if (isWideScreen) {
                        Modifier.width(400.dp)
                    } else Modifier
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = VibeIcons.Logo,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(com.fomaxtro.vibeplayer.core.designsystem.R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = stringResource(R.string.media_files_permission),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            VibeButton(
                onClick = {},
                text = stringResource(R.string.allow_access)
            )
        }
    }
}

@DevicePreviews
@Composable
private fun PermissionScreenPreview() {
    VibePlayerTheme {
        PermissionScreen()
    }
}