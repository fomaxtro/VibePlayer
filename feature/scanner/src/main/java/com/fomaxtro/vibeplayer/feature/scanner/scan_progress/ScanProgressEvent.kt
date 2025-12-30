package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface ScanProgressEvent {
    data object ScanFinish : ScanProgressEvent
    data class ShoMessage(val message: UiText) : ScanProgressEvent
}