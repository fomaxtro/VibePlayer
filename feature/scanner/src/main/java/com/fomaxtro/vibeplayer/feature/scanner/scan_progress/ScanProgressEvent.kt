package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

sealed interface ScanProgressEvent {
    data object ScanFinish : ScanProgressEvent
}