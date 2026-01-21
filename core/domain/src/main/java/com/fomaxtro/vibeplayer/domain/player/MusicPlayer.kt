package com.fomaxtro.vibeplayer.domain.player

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface MusicPlayer {
    val playerState: Flow<PlayerState>
    val playbackPosition: Flow<Duration>

    fun play(index: Int)
    fun play(playlist: List<MediaTrack>, index: Int)
    fun pause()
    fun resume()
    fun togglePlayPause()
    fun stop()
    fun setPlaylist(playlist: List<MediaTrack>)
    fun clearPlaylist()
    fun skipNext()
    fun skipPrevious()
    fun seekTo(duration: Duration)
    fun setShuffleModeEnabled(isEnabled: Boolean)
    fun setRepeatMode(repeatMode: RepeatMode)
    fun toggleShuffle()
}