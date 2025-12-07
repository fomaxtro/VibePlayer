package com.fomaxtro.vibeplayer.feature.library.scan_music

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.DevicePreviews
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.component.OutlinedRadioButton
import com.fomaxtro.vibeplayer.feature.library.component.RadioGroup
import com.fomaxtro.vibeplayer.feature.library.component.ScanIndicator
import com.fomaxtro.vibeplayer.feature.library.mapper.getLabel
import com.fomaxtro.vibeplayer.feature.library.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanMusicScreen(
    onNavigateBackClick: () -> Unit,
    viewModel: ScanMusicViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanMusicEvent.NavigateBack -> onNavigateBackClick()
        }
    }

    ScanMusicScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ScanMusicAction.OnNavigateBackClick -> onNavigateBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun ScanMusicScreen(
    state: ScanMusicUiState,
    onAction: (ScanMusicAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                title = stringResource(R.string.scan_music),
                onNavigateBackClick = {
                    onAction(ScanMusicAction.OnNavigateBackClick)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScanIndicator(
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
                                ScanMusicAction.OnDurationConstraintSelected(
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
                                ScanMusicAction.OnSizeConstraintSelected(
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
                    onAction(ScanMusicAction.OnScanClick)
                },
                text = if (state.isScanning) {
                    stringResource(R.string.scanning)
                } else {
                    stringResource(R.string.scan)
                },
                modifier = Modifier.fillMaxWidth(),
                loading = state.isScanning
            )
        }
    }
}

@DevicePreviews
@Composable
private fun ScanMusicScreenPreview() {
    VibePlayerTheme {
        ScanMusicScreen(
            state = ScanMusicUiState(
                isScanning = true
            )
        )
    }
}