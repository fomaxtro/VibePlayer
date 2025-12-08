package com.fomaxtro.vibeplayer.feature.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.ui.util.Resource
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.feature.player.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val songId: Long,
    private val musicPlayer: MusicPlayer,
    private val songRepository: SongRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PlayerState())

    private fun loadSong() = viewModelScope.launch {
        val song = songRepository.getSongById(songId)

        if (song != null) {
            _state.update {
                it.copy(playingSong = Resource.Success(song))
            }

            musicPlayer.play(song)
        } else {
            _state.update {
                it.copy(
                    playingSong = Resource.Error(
                        error = UiText.StringResource(R.string.song_not_found)
                    )
                )
            }
        }
    }

    init {
        loadSong()
    }

    val state: StateFlow<PlayerUiState> = combine(
        _state,
        musicPlayer.currentPosition,
        musicPlayer.isPlaying
    ) { state, currentPosition, isPlaying ->
        when (state.playingSong) {
            Resource.Loading -> PlayerUiState.Loading

            is Resource.Error -> PlayerUiState.Error(
                error = state.playingSong.error
            )

            is Resource.Success -> {
                PlayerUiState.Success(
                    songs = state.songs,
                    playingSong = state.playingSong.data,
                    currentPosition = currentPosition,
                    isPlaying = isPlaying
                )
            }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PlayerUiState.Loading
        )

    private data class PlayerState(
        val songs: List<Song> = emptyList(),
        val playingSong: Resource<Song> = Resource.Loading
    )

    override fun onCleared() {
        super.onCleared()

        musicPlayer.release()
    }
}