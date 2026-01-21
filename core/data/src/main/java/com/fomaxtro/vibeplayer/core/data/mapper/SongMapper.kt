package com.fomaxtro.vibeplayer.core.data.mapper

import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration.Companion.milliseconds

fun SongEntity.toDomain() = Song(
    id = id,
    title = title,
    artist = artist,
    duration = durationMillis.milliseconds,
    filePath = filePath,
    sizeBytes = sizeBytes,
    albumArtUri = albumArtUri,
    isFavourite = isFavourite
)

fun Song.toEntity() = SongEntity(
    id = id,
    title = title,
    artist = artist,
    durationMillis = duration.inWholeMilliseconds,
    albumArtUri = albumArtUri,
    filePath = filePath,
    sizeBytes = sizeBytes,
    isFavourite = isFavourite,
)

fun List<SongEntity>.toDomain(): List<Song> = map { it.toDomain() }