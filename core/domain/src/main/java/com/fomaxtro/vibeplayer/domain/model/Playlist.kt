package com.fomaxtro.vibeplayer.domain.model

data class Playlist(
    val id: Long,
    val name: String,
    val songsCount: Int,
    val albumArtUri: String?
)
