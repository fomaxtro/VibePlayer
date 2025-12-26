package com.fomaxtro.vibeplayer.domain.player

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface MusicPlayer {
    val playerState: Flow<PlayerState>
    val playbackPosition: Flow<Duration>

    fun play(index: Int)
    fun pause()
    fun resume()
    fun togglePlayPause()
    fun stop()
    fun setPlaylist(playlist: List<Song>)
    fun clearPlaylist()
    fun skipNext()
    fun skipPrevious()
    fun seekTo(duration: Duration)
}