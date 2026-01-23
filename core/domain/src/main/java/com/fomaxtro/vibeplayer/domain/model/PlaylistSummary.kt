package com.fomaxtro.vibeplayer.domain.model

data class PlaylistSummary(
    val playlists: List<Playlist>,
    val favouriteSongsCount: Int
)
