package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface ScanOptionsEvent {
    data object NavigateBack : ScanOptionsEvent
    data class ShowMessage(val message: UiText) : ScanOptionsEvent
    data class OnScanResult(val songsCount: Int) : ScanOptionsEvent
}