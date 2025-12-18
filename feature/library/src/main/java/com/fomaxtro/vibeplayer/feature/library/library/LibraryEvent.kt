package com.fomaxtro.vibeplayer.feature.library.library

sealed interface LibraryEvent {
    data class PlaySong(val songIndex: Int) : LibraryEvent
}