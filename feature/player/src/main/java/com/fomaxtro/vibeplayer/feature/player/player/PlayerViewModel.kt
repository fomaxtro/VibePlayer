package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PlayerViewModel(
    private val player: MusicPlayer
) : ViewModel() {
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
            is PlayerAction.PlaySong -> playSong(action.songIndex)
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