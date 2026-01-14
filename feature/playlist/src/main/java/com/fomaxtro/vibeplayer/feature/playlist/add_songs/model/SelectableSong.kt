package com.fomaxtro.vibeplayer.feature.playlist.add_songs.model

import com.fomaxtro.vibeplayer.domain.model.Song

data class SelectableSong(
    val song: Song,
    val selected: Boolean = false
)
