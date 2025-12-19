package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration

@Immutable
data class PlayerUiState(
    val playlist: List<Song> = emptyList(),
    val playingSong: Song? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Duration = Duration.ZERO,
    val canSkipPrevious: Boolean = false,
    val canSkipNext: Boolean = false
) {
    val currentSongProgress: Float = playingSong?.let { playingSong ->
        (currentPosition / playingSong.duration).toFloat()
    } ?: 0f
}
