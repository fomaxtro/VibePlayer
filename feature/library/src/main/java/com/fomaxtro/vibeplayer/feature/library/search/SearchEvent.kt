package com.fomaxtro.vibeplayer.feature.library.search

sealed interface SearchEvent {
    data object PlaySong : SearchEvent
}