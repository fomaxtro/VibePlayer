package com.fomaxtro.vibeplayer.domain.player

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentSong: MediaTrack? = null,
    val playlist: List<MediaTrack> = emptyList(),
    val canSkipNext: Boolean = false,
    val canSkipPrevious: Boolean = false,
    val isShuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
)
