package com.fomaxtro.vibeplayer.core.data.mapper

import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration.Companion.milliseconds

fun SongEntity.toSong() = Song(
    id = id,
    title = title,
    artist = artist,
    duration = durationMillis.milliseconds,
    filePath = filePath,
    sizeBytes = sizeBytes,
    albumArtUri = albumArtUri
)