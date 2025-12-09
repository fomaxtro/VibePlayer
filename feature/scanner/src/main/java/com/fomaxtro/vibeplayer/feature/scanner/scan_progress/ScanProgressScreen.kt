package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIndicator
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.feature.scanner.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ScanProgressScreen(
    onScanFinish: () -> Unit,
    viewModel: ScanProgressViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanProgressEvent.ScanFinish -> onScanFinish()
        }
    }

    ScanProgressScreen()
}

@Composable
private fun ScanProgressScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

@Preview
@Composable
private fun ScanProgressScreenPreview() {
    VibePlayerTheme {
        ScanProgressScreen()
    }
}