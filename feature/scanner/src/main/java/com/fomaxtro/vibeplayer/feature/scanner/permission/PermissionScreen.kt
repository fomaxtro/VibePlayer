package com.fomaxtro.vibeplayer.feature.scanner.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.DevicePreviews
import com.fomaxtro.vibeplayer.feature.scanner.R

@Composable
internal fun PermissionScreen(
    onPermissionGranted: () -> Unit
) {
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    var showPermissionRationale by rememberSaveable {
        mutableStateOf(false)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            showPermissionRationale = true
        }
    }

    PermissionScreen(
        onAction = { action -> 
            when (action) {
                PermissionAction.OnAllowAccessClick -> {
                    permissionLauncher.launch(permissionToRequest)
                }
            }
        }
    )

    if (showPermissionRationale) {
        AlertDialog(
            onDismissRequest = { showPermissionRationale = false },
            text = {
                Text(text = stringResource(R.string.audio_permission_rationale))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionRationale = false

                        permissionLauncher.launch(permissionToRequest)
                    }
                ) {
                    Text(text = stringResource(R.string.try_again))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPermissionRationale = false }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
}

@Composable
internal fun PermissionScreen(
    onAction: (PermissionAction) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                onClick = {
                    onAction(PermissionAction.OnAllowAccessClick)
                },
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