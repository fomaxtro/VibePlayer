package com.fomaxtro.vibeplayer.feature.playlist.playlist

sealed interface PlaylistAction {
    data object OnCreatePlaylistClick : PlaylistAction
    data object OnDismissPlaylistCreateSheet : PlaylistAction
}