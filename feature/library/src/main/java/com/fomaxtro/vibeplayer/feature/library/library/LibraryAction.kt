package com.fomaxtro.vibeplayer.feature.library.library

import com.fomaxtro.vibeplayer.domain.model.Song

sealed interface LibraryAction {
    data object OnScanMusicClick : LibraryAction
    data class OnSongClick(val song: Song) : LibraryAction
    data object OnSearchClick : LibraryAction
    data object OnShuffleClick : LibraryAction
    data object OnPlayClick : LibraryAction
}