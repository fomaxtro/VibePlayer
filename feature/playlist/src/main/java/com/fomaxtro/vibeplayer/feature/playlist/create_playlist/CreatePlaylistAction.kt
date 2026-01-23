package com.fomaxtro.vibeplayer.feature.playlist.create_playlist

sealed interface CreatePlaylistAction {
    data object OnCancelClick : CreatePlaylistAction
    data object OnCreateClick : CreatePlaylistAction
}