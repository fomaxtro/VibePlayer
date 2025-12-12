package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PlayerViewModel(
    songIndex: Int,
    private val player: MusicPlayer
) : ViewModel() {
    init {
        player.play(songIndex)
    }

    val state: StateFlow<PlayerUiState> = combine(
        player.playerState,
        player.playbackPosition
    ) { playerState, playbackPosition ->
        PlayerUiState(
            playlist = playerState.playlist,
            playingSong = playerState.currentSong,
            isPlaying = playerState.isPlaying,
            currentPosition = playbackPosition,
            canSkipPrevious = playerState.canSkipPrevious,
            canSkipNext = playerState.canSkipNext
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
            else -> Unit
        }
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

    override fun onCleared() {
        player.stop()
    }
}