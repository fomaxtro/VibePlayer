package com.fomaxtro.vibeplayer.feature.library.scan_music

import com.fomaxtro.vibeplayer.feature.library.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint

sealed interface ScanMusicAction {
    data object OnNavigateBackClick : ScanMusicAction

    data class OnDurationConstraintSelected(
        val durationConstraint: DurationConstraint
    ) : ScanMusicAction

    data class OnSizeConstraintSelected(
        val sizeConstraint: SizeConstraint
    ) : ScanMusicAction

    data object OnScanClick : ScanMusicAction
}