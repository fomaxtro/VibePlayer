package com.fomaxtro.vibeplayer.feature.library.library

sealed interface LibraryAction {
    data object OnScanAgainClick : LibraryAction
}