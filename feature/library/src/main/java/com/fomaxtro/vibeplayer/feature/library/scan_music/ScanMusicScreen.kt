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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeInnerTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.DevicePreviews
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.component.OutlinedRadioButton
import com.fomaxtro.vibeplayer.feature.library.component.RadioGroup
import com.fomaxtro.vibeplayer.feature.library.component.ScanIndicator
import com.fomaxtro.vibeplayer.feature.library.mapper.getLabel
import com.fomaxtro.vibeplayer.feature.library.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint

@Composable
fun ScanMusicScreen(
    state: ScanMusicUiState
) {
    Scaffold(
        topBar = {
            VibeInnerTopAppBar(
                title = stringResource(R.string.scan_music)
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
                        onClick = {},
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
                        onClick = {},
                        text = constraint.getLabel(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            VibeButton(
                onClick = {},
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