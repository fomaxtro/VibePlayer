package com.fomaxtro.vibeplayer.domain.mapper

import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MediaTrack

fun Song.toMediaTrack() = MediaTrack(
    id = id,
    filePath = filePath
)

fun List<Song>.toMediaTrack(): List<MediaTrack> = map { it.toMediaTrack() }