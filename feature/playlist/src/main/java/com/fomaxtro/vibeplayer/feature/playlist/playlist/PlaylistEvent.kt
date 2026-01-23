package com.fomaxtro.vibeplayer.feature.playlist.playlist

sealed interface PlaylistEvent {
    data class PlaylistCreated(val playlistId: Long) : PlaylistEvent
}