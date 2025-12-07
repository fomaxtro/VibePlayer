package com.fomaxtro.vibeplayer.feature.library.scan_music

sealed interface ScanMusicEvent {
    data object NavigateBack : ScanMusicEvent
}