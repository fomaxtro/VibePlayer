package com.fomaxtro.vibeplayer.core.player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class ExoPlayerMusicPlayer(
    private val player: ExoPlayer
) : MusicPlayer {
    private var _isPlaying = MutableStateFlow(false)
    override val isPlaying: Flow<Boolean> = _isPlaying.asStateFlow()

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }
    }

    override val currentPosition: Flow<Duration> = flow {
        while (true) {
            emit(player.currentPosition.milliseconds)
            delay(if (player.isPlaying) 200L else 1000L)
        }
    }

    init {
        player.addListener(playerListener)
    }

    override fun play(song: Song) {
        val mediaItem = MediaItem.fromUri(song.filePath.toUri())

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun resume() {
        player.play()
    }

    override fun release() {
        player.removeListener(playerListener)
    }
}