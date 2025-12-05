package com.fomaxtro.vibeplayer.domain.model

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val durationMillis: Long,
    val filePath: String,
    val sizeBytes: Long,
    val albumArtUri: String
)
