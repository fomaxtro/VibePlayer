package com.fomaxtro.vibeplayer.feature.playlist.create_playlist

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface CreatePlaylistEvent {
    data class ShowMessage(val message: UiText) : CreatePlaylistEvent
    data class PlaylistCreated(val playlistId: Long) : CreatePlaylistEvent
    data object Cancel : CreatePlaylistEvent
}