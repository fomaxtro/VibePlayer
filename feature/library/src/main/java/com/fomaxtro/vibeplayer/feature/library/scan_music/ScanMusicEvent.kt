package com.fomaxtro.vibeplayer.feature.library.scan_music

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface ScanMusicEvent {
    data object NavigateBack : ScanMusicEvent
    data class ShowMessage(val message: UiText) : ScanMusicEvent
}