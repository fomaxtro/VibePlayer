package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIndicator
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.scanner.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ScanProgressScreen(
    onScanFinish: () -> Unit,
    onScanOptions: () -> Unit,
    viewModel: ScanProgressViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanProgressEvent.ScanFinish -> onScanFinish()
            is ScanProgressEvent.ShoMessage -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asString(context)
                )
            }
        }
    }

    ScanProgressScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ScanProgressAction.OnScanOptionsClick -> onScanOptions()
                else -> viewModel.onAction(action)
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun ScanProgressScreen(
    state: ScanProgressUiState,
    onAction: (ScanProgressAction) -> Unit = {},
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            VibeMainTopAppBar(
                actions = {
                    VibeScanIconButton(
                        onClick = {
                            onAction(ScanProgressAction.OnScanOptionsClick)
                        }
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                ScanProgressUiState.Empty -> {
                    Text(
                        text = stringResource(R.string.no_music_found),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.no_music_found_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    VibeButton(
                        onClick = {
                            onAction(ScanProgressAction.OnScanAgainClick)
                        },
                        text = stringResource(R.string.scan_again)
                    )
                }

                ScanProgressUiState.Scanning -> {
                    VibeScanIndicator(
                        scanning = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.scanning_music),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScanProgressScreenPreview() {
    VibePlayerTheme {
        ScanProgressScreen(
            state = ScanProgressUiState.Empty,
            snackbarHostState = SnackbarHostState()
        )
    }
}