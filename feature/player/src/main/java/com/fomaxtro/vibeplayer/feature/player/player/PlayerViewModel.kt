package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

class PlayerViewModel(
    private val player: MusicPlayer
) : ViewModel() {
    private val isSeeking = MutableStateFlow(false)

    val state: StateFlow<PlayerUiState> = combine(
        player.playerState,
        player.playbackPosition,
        isSeeking
    ) { playerState, playbackPosition, isSeeking ->
        PlayerUiState(
            playlist = playerState.playlist,
            playingSong = playerState.currentSong,
            isPlaying = playerState.isPlaying,
            playingSongPosition = playbackPosition,
            canSkipPrevious = playerState.canSkipPrevious,
            canSkipNext = playerState.canSkipNext,
            isSeeking = isSeeking
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlayerUiState()
        )

    fun onAction(action: PlayerAction) {
        when (action) {
            PlayerAction.OnPlayPauseToggle -> onPlayPauseToggle()
            PlayerAction.OnSkipNextClick -> onSkipNextClick()
            PlayerAction.OnSkipPreviousClick -> onSkipPreviousClick()
            is PlayerAction.PlaySong -> playSong(action.songIndex)
            is PlayerAction.OnSeekTo -> onSeekTo(action.songProgressFactor)
            PlayerAction.OnSeekCancel -> onSeekCancel()
            PlayerAction.OnSeekStarted -> onSeekStarted()
        }
    }

    private fun onSeekStarted() {
        isSeeking.value = true
    }

    private fun onSeekCancel() {
        isSeeking.value = false
    }

    private fun onSeekTo(songProgressFactor: Float) = viewModelScope.launch {
        state.value.playingSong?.let { song ->
            val duration = (song.duration.inWholeMilliseconds * songProgressFactor)
                .roundToInt()
                .milliseconds

            player.seekTo(duration)
            // Wait for the seek to complete before updating the isSeeking state
            delay(250)
            isSeeking.value = false
        }
    }

    private fun playSong(songIndex: Int) {
        player.play(songIndex)
    }

    private fun onSkipPreviousClick() {
        player.skipPrevious()
    }

    private fun onSkipNextClick() {
        player.skipNext()
    }

    private fun onPlayPauseToggle() {
        player.togglePlayPause()
    }
}