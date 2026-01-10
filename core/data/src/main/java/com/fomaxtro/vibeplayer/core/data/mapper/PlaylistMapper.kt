package com.fomaxtro.vibeplayer.core.data.mapper

import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist

fun NewPlaylist.toEntity() = PlaylistEntity(
    name = name,
    albumArtUri = null
)