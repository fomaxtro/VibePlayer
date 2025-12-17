package com.fomaxtro.vibeplayer.feature.library.library

sealed interface LibraryAction {
    data object OnScanMusicClick : LibraryAction
    data class OnSongClick(val songIndex: Int) : LibraryAction
    data class OnSearchQueryChange(val query: String) : LibraryAction
    data object OnSearchClick : LibraryAction
    data object OnClearClick : LibraryAction
    data object OnCancelClick : LibraryAction
    data object OnShuffleClick : LibraryAction
    data object OnPlayClick : LibraryAction
}