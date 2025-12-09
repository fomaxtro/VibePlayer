package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIndicator
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.scanner.R
import com.fomaxtro.vibeplayer.feature.scanner.component.OutlinedRadioButton
import com.fomaxtro.vibeplayer.feature.scanner.component.RadioGroup
import com.fomaxtro.vibeplayer.feature.scanner.mapper.getLabel
import com.fomaxtro.vibeplayer.feature.scanner.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.scanner.model.SizeConstraint
import org.koin.androidx.compose.koinViewModel
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Composable
fun ScanOptionsScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScanOptionsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanOptionsEvent.NavigateBack -> onNavigateBack()
            is ScanOptionsEvent.ShowMessage -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asString(context)
                )
            }
        }
    }

    ScanOptionsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ScanOptionsAction.OnNavigateBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun ScanOptionsScreen(
    state: ScanOptionsUiState,
    onAction: (ScanOptionsAction) -> Unit = {},
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                title = stringResource(R.string.scan_music),
                onNavigateBackClick = {
                    onAction(ScanOptionsAction.OnNavigateBackClick)
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
                .then(
                    if (isWideScreen) {
                        Modifier
                            .wrapContentWidth()
                            .width(400.dp)
                    } else Modifier
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            VibeScanIndicator(
                scanning = state.isScanning
            )

            Spacer(modifier = Modifier.height(24.dp))

            RadioGroup(
                title = stringResource(R.string.ignore_duration_less_than)
            ) {
                DurationConstraint.entries.forEach { constraint ->
                    OutlinedRadioButton(
                        selected = state.selectedDurationConstraint == constraint,
                        onClick = {
                            onAction(
                                ScanOptionsAction.OnDurationConstraintSelected(
                                    durationConstraint = constraint
                                )
                            )
                        },
                        text = constraint.getLabel(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RadioGroup(
                title = stringResource(R.string.ignore_size_less_than)
            ) {
                SizeConstraint.entries.forEach { constraint ->
                    OutlinedRadioButton(
                        selected = state.selectedSizeConstraint == constraint,
                        onClick = {
                            onAction(
                                ScanOptionsAction.OnSizeConstraintSelected(
                                    sizeConstraint = constraint
                                )
                            )
                        },
                        text = constraint.getLabel(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            VibeButton(
                onClick = {
                    onAction(ScanOptionsAction.OnScanClick)
                },
                text = if (state.isScanning) {
                    stringResource(R.string.scanning)
                } else {
                    stringResource(DesignR.string.scan)
                },
                modifier = Modifier.fillMaxWidth(),
                loading = state.isScanning
            )
        }
    }
}

@DevicePreviews
@Composable
private fun ScanOptionsScreenPreview() {
    VibePlayerTheme {
        ScanOptionsScreen(
            state = ScanOptionsUiState(
                isScanning = true
            ),
            snackbarHostState = SnackbarHostState()
        )
    }
}