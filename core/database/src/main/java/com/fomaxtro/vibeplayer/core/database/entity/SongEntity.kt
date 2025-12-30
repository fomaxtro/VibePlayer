package com.fomaxtro.vibeplayer.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "songs",
    indices = [
        Index(value = ["file_path"], unique = true),
        Index(value = ["title"]),
        Index(value = ["artist"])
    ]
)
data class SongEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artist: String,
    @ColumnInfo("duration_millis")
    val durationMillis: Long,
    @ColumnInfo("album_art_uri")
    val albumArtUri: String?,
    @ColumnInfo("file_path")
    val filePath: String,
    @ColumnInfo("size_bytes")
    val sizeBytes: Long
)
