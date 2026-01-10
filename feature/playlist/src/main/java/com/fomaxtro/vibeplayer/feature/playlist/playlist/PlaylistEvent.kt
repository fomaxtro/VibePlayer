package com.fomaxtro.vibeplayer.feature.playlist.playlist

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface PlaylistEvent {
    data class ShowSystemMessage(val message: UiText) : PlaylistEvent
    data class PlaylistCreated(val playlistId: Long) : PlaylistEvent
}