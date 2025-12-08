package com.fomaxtro.vibeplayer.domain.player

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface MusicPlayer {
    val currentPosition: Flow<Duration>
    val isPlaying: Flow<Boolean>

    fun play(song: Song)
    fun pause()
    fun resume()
    fun release()
}