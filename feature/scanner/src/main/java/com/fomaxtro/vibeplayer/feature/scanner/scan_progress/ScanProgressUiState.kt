package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ScanProgressUiState {
    data object Scanning : ScanProgressUiState
    data object Empty : ScanProgressUiState
}