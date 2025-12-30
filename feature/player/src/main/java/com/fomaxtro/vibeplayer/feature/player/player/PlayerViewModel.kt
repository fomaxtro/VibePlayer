package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.player.PlayerState
import com.fomaxtro.vibeplayer.domain.player.RepeatMode
import com.fomaxtro.vibeplayer.feature.player.R
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
    private val player: MusicPlayer,
    private val snackbarController: SnackbarController
) : ViewModel() {
    private val isSeeking = MutableStateFlow(false)
    private val playerState = player.playerState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlayerState()
        )

    val state: StateFlow<PlayerUiState> = combine(
        playerState,
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
            isSeeking = isSeeking,
            isShuffleEnabled = playerState.isShuffleEnabled,
            repeatMode = playerState.repeatMode
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
            PlayerAction.OnRepeatModeClick -> onRepeatModeClick()
            PlayerAction.OnToggleShuffleClick -> onToggleShuffleClick()
        }
    }

    private fun onToggleShuffleClick() = viewModelScope.launch {
        val isShuffledEnabled = !playerState.value.isShuffleEnabled

        player.setShuffleModeEnabled(isShuffledEnabled)
        snackbarController.showSnackbar(
            message = if (isShuffledEnabled) {
                UiText.StringResource(R.string.shuffle_enabled)
            } else {
                UiText.StringResource(R.string.shuffle_disabled)
            }
        )
    }

    private fun onRepeatModeClick() = viewModelScope.launch {
        when (playerState.value.repeatMode) {
            RepeatMode.OFF -> {
                player.setRepeatMode(RepeatMode.ALL)
                snackbarController.showSnackbar(
                    message = UiText.StringResource(R.string.repeat_all)
                )
            }
            RepeatMode.ALL -> {
                player.setRepeatMode(RepeatMode.ONE)
                snackbarController.showSnackbar(
                    message = UiText.StringResource(R.string.repeat_one)
                )
            }
            RepeatMode.ONE -> {
                player.setRepeatMode(RepeatMode.OFF)
                snackbarController.showSnackbar(
                    message = UiText.StringResource(R.string.repeat_off)
                )
            }
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