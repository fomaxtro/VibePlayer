package com.fomaxtro.vibeplayer.core.data.mapper

import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistWithMetadata
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.model.Playlist

fun NewPlaylist.toEntity() = PlaylistEntity(
    name = name
)

fun PlaylistWithMetadata.toDomain() = Playlist(
    id = id,
    name = name,
    songsCount = songCount,
    albumArtUri = albumArtUri,
)

fun List<PlaylistWithMetadata>.toDomain(): List<Playlist> = map { it.toDomain() }