package com.fomaxtro.vibeplayer.feature.playlist.add_songs

sealed interface AddSongsEvent {
    data object PlaylistCreated : AddSongsEvent
}