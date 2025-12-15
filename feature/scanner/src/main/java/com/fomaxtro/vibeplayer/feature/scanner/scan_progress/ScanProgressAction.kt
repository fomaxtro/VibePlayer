package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

sealed interface ScanProgressAction {
    data object OnScanOptionsClick : ScanProgressAction
    data object OnScanAgainClick : ScanProgressAction
}