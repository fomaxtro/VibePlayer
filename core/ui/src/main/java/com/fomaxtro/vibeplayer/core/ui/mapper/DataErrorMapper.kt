package com.fomaxtro.vibeplayer.core.ui.mapper

import com.fomaxtro.vibeplayer.core.ui.R
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.error.DataError

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Local.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
        else -> UiText.StringResource(R.string.error_generic)
    }
}