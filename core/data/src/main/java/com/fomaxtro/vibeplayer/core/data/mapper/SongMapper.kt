package com.fomaxtro.vibeplayer.core.data.mapper

import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import com.fomaxtro.vibeplayer.domain.model.Song

fun SongEntity.toSong() = Song(
    id = id,
    title = title,
    artist = artist,
    durationMillis = durationMillis,
    filePath = filePath,
    sizeBytes = sizeBytes,
    albumArtUri = albumArtUri
)