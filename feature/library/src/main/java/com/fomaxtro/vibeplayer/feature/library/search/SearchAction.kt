package com.fomaxtro.vibeplayer.feature.library.search

import com.fomaxtro.vibeplayer.domain.model.Song

sealed interface SearchAction {
    data object OnCancelClick : SearchAction
    data class OnSongClick(val song: Song) : SearchAction
}