package com.fomaxtro.vibeplayer.domain.model

import kotlin.time.Duration

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Duration,
    val filePath: String,
    val sizeBytes: Long,
    val albumArtUri: String?,
    val isFavourite: Boolean
)
