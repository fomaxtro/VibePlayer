package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration

@Immutable
sealed interface PlayerUiState {
    data object Loading : PlayerUiState

    data class Success(
        val songs: List<Song> = emptyList(),
        val playingSong: Song? = null,
        val isPlaying: Boolean = false,
        val currentPosition: Duration = Duration.ZERO
    ) : PlayerUiState {
        val currentSongProgress: Float = if (playingSong != null) {
            (currentPosition / playingSong.duration).toFloat()
        } else 0f

        val canSkipPrevious: Boolean = songs.isNotEmpty() && songs.indexOf(playingSong) > 0
        val canSkipNext: Boolean = songs.isNotEmpty() && songs.indexOf(playingSong) < songs.lastIndex
    }
}
