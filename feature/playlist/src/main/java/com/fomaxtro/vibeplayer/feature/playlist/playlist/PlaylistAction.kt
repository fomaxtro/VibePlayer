package com.fomaxtro.vibeplayer.feature.playlist.playlist

sealed interface PlaylistAction {
    data object OnAddPlaylistClick : PlaylistAction
    data object OnDismissPlaylistCreateSheet : PlaylistAction
    data object OnConfirmCreatePlaylist : PlaylistAction
}