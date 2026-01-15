package com.fomaxtro.vibeplayer.core.database.entity

import androidx.room.ColumnInfo

data class PlaylistWithMetadata(
    val id: Long,
    val name: String,
    @ColumnInfo("song_count")
    val songCount: Int,
    @ColumnInfo("album_art_uri")
    val albumArtUri: String?
)
