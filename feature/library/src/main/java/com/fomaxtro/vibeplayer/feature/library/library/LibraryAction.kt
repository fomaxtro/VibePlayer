package com.fomaxtro.vibeplayer.feature.library.library

sealed interface LibraryAction {
    data object OnScanMusicClick : LibraryAction
    data class OnSongClick(val songIndex: Int) : LibraryAction
}