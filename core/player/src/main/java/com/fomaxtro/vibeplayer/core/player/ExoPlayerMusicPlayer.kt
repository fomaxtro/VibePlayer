package com.fomaxtro.vibeplayer.core.player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.player.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class ExoPlayerMusicPlayer(
    private val player: ExoPlayer
) : MusicPlayer {
    private val _playerState = MutableStateFlow(PlayerState())
    override val playerState: Flow<PlayerState> = _playerState.asStateFlow()

    override val playbackPosition: Flow<Duration> = flow {
        while (true) {
            emit(player.currentPosition.milliseconds)
            delay(if (player.isPlaying) 200L else 1000L)
        }
    }

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _playerState.update { it.copy(isPlaying = isPlaying) }
        }

        override fun onMediaItemTransition(
            mediaItem: MediaItem?,
            reason: Int
        ) {
            if (mediaItem != null && _playerState.value.playlist.isNotEmpty()) {
                _playerState.update {
                    it.copy(
                        currentSong = _playerState.value.playlist[player.currentMediaItemIndex]
                    )
                }
            } else {
                _playerState.update {
                    it.copy(
                        currentSong = null
                    )
                }
            }
        }
    }

    init {
        player.addListener(playerListener)
    }

    private fun updateControlsState() {
        _playerState.update {
            it.copy(
                canSkipPrevious = player.currentMediaItemIndex - 1 >= 0,
                canSkipNext = player.currentMediaItemIndex + 1 <= it.playlist.lastIndex
            )
        }
    }

    override fun play(index: Int) {
        if (index >= 0 && index <= _playerState.value.playlist.lastIndex) {
            if (player.isPlaying) {
                player.stop()
            }

            player.seekTo(index, 0)
            player.prepare()
            player.play()

            _playerState.update { it.copy(currentSong = it.playlist[index],) }
            updateControlsState()
        }
    }

    override fun pause() {
        if (player.isPlaying) {
            player.pause()
        }
    }

    override fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    override fun resume() {
        if (!player.isPlaying && player.playbackState != Player.STATE_IDLE) {
            player.play()
        }
    }

    override fun stop() {
        player.stop()
    }

    override fun setPlaylist(playlist: List<Song>) {
        val mediaItems = playlist.map {
            MediaItem.fromUri(it.filePath.toUri())
        }

        player.stop()
        player.clearMediaItems()
        player.addMediaItems(mediaItems)

        _playerState.update { it.copy(playlist = playlist) }
    }

    override fun clearPlaylist() {
        player.stop()
        player.clearMediaItems()

        _playerState.update {
            it.copy(
                playlist = emptyList()
            )
        }
    }

    override fun skipNext() {
        player.seekToNext()
        updateControlsState()
    }

    override fun skipPrevious() {
        player.seekToPrevious()
        updateControlsState()
    }
}