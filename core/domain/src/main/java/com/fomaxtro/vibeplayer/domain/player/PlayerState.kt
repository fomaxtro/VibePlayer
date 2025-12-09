package com.fomaxtro.vibeplayer.domain.player

import com.fomaxtro.vibeplayer.domain.model.Song

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentSong: Song? = null,
    val playlist: List<Song> = emptyList(),
    val canSkipNext: Boolean = false,
    val canSkipPrevious: Boolean = false
)
