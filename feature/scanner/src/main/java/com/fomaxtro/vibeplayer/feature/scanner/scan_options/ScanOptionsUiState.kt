package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import com.fomaxtro.vibeplayer.feature.scanner.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.scanner.model.SizeConstraint

data class ScanOptionsUiState(
    val isScanning: Boolean = false,
    val selectedDurationConstraint: DurationConstraint = DurationConstraint.THIRTY_SECONDS,
    val selectedSizeConstraint: SizeConstraint = SizeConstraint.ONE_HUNDRED_KB
)
