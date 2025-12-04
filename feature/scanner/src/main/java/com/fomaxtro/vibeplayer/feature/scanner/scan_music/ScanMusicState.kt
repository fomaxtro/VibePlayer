package com.fomaxtro.vibeplayer.feature.scanner.scan_music

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ScanMusicState {
    data object Loading : ScanMusicState
    data object Empty : ScanMusicState
}
