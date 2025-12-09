package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import com.fomaxtro.vibeplayer.feature.scanner.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.scanner.model.SizeConstraint

sealed interface ScanOptionsAction {
    data object OnNavigateBackClick : ScanOptionsAction

    data class OnDurationConstraintSelected(
        val durationConstraint: DurationConstraint
    ) : ScanOptionsAction

    data class OnSizeConstraintSelected(
        val sizeConstraint: SizeConstraint
    ) : ScanOptionsAction

    data object OnScanClick : ScanOptionsAction
}