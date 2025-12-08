package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration

@Immutable
sealed interface PlayerUiState {
    data object Loading : PlayerUiState

    data class Success(
        val songs: List<Song> = emptyList(),
        val playingSong: Song,
        val isPlaying: Boolean = false,
        val currentPosition: Duration = Duration.ZERO
    ) : PlayerUiState {
        val currentSongProgress: Float = (currentPosition / playingSong.duration).toFloat()
        val canSkipPrevious: Boolean = songs.isNotEmpty() && songs.indexOf(playingSong) > 0
        val canSkipNext: Boolean = songs.isNotEmpty() && songs.indexOf(playingSong) < songs.lastIndex
    }

    data class Error(val error: UiText) : PlayerUiState
}
