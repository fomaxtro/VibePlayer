package com.fomaxtro.vibeplayer.feature.library.scan_music

import com.fomaxtro.vibeplayer.feature.library.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint

data class ScanMusicUiState(
    val isScanning: Boolean = false,
    val selectedDurationConstraint: DurationConstraint = DurationConstraint.THIRTY_SECONDS,
    val selectedSizeConstraint: SizeConstraint = SizeConstraint.ONE_HUNDRED_KB
)
