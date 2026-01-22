package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.player.PlayerState
import com.fomaxtro.vibeplayer.domain.player.RepeatMode
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.feature.player.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

class PlayerViewModel(
    private val player: MusicPlayer,
    private val songRepository: SongRepository
) : ViewModel() {
    private val eventChannel = Channel<PlayerEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isSeeking = MutableStateFlow(false)
    private val playerState = player.playerState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlayerState()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val playlist = playerState
        .map { state -> state.playlist.map { it.id } }
        .distinctUntilChanged()
        .flatMapLatest { ids ->
            songRepository.getSongsByIdsStream(ids)
        }

    private val playingSong: StateFlow<Song?> = playerState
        .map { it.currentSong }
        .distinctUntilChanged()
        .combine(playlist) { playingSong, playlist ->
            playlist.find { it.id == playingSong?.id }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    val state: StateFlow<PlayerUiState> = combine(
        playerState,
        player.playbackPosition,
        isSeeking,
        playlist,
        playingSong
    ) { playerState, playbackPosition, isSeeking, playlist, playingSong ->
        PlayerUiState(
            playlist = playlist,
            playingSong = playingSong,
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
            PlayerAction.OnPlayPauseToggle -> togglePlayPause()
            PlayerAction.OnSkipNextClick -> skipNext()
            PlayerAction.OnSkipPreviousClick -> skipPrevious()
            is PlayerAction.PlaySong -> playSong(action.songIndex)
            is PlayerAction.OnSeekTo -> seekTo(action.songProgressFactor)
            PlayerAction.OnSeekCancel -> seekCancel()
            PlayerAction.OnSeekStarted -> seekStarted()
            PlayerAction.OnCycleRepeatMode -> cycleRepeatMode()
            PlayerAction.OnShuffleToggle -> toggleShuffle()
            PlayerAction.OnFavouriteToggle -> toggleFavourite()
            else -> Unit
        }
    }

    private fun toggleFavourite() = viewModelScope.launch {
        val playingSong = playingSong.value ?: return@launch

        songRepository.updateSong(
            playingSong.copy(
                isFavourite = !playingSong.isFavourite
            )
        )
    }

    private fun toggleShuffle() = viewModelScope.launch {
        val isShuffledEnabled = !playerState.value.isShuffleEnabled

        player.setShuffleModeEnabled(isShuffledEnabled)

        eventChannel.send(
            PlayerEvent.ShowSystemMessage(
                message = if (isShuffledEnabled) {
                    UiText.StringResource(R.string.shuffle_enabled)
                } else {
                    UiText.StringResource(R.string.shuffle_disabled)
                }
            )
        )
    }

    private fun cycleRepeatMode() = viewModelScope.launch {
        when (playerState.value.repeatMode) {
            RepeatMode.OFF -> {
                player.setRepeatMode(RepeatMode.ALL)

                eventChannel.send(
                    PlayerEvent.ShowSystemMessage(
                        message = UiText.StringResource(R.string.repeat_all)
                    )
                )
            }
            RepeatMode.ALL -> {
                player.setRepeatMode(RepeatMode.ONE)

                eventChannel.send(
                    PlayerEvent.ShowSystemMessage(
                        message = UiText.StringResource(R.string.repeat_one)
                    )
                )
            }
            RepeatMode.ONE -> {
                player.setRepeatMode(RepeatMode.OFF)

                eventChannel.send(
                    PlayerEvent.ShowSystemMessage(
                        message = UiText.StringResource(R.string.repeat_off)
                    )
                )
            }
        }
    }

    private fun seekStarted() {
        isSeeking.value = true
    }

    private fun seekCancel() {
        isSeeking.value = false
    }

    private fun seekTo(songProgressFactor: Float) = viewModelScope.launch {
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

    private fun skipPrevious() {
        player.skipPrevious()
    }

    private fun skipNext() {
        player.skipNext()
    }

    private fun togglePlayPause() {
        player.togglePlayPause()
    }
}