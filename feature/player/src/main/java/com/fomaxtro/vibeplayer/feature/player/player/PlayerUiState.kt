package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.RepeatMode
import kotlin.time.Duration

@Immutable
data class PlayerUiState(
    val playlist: List<Song> = emptyList(),
    val playingSong: Song? = null,
    val isPlaying: Boolean = false,
    val playingSongPosition: Duration = Duration.ZERO,
    val canSkipPrevious: Boolean = false,
    val canSkipNext: Boolean = false,
    val isSeeking: Boolean = false,
    val isShuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
) {
    val playingSongProgress: Float = playingSong?.let { playingSong ->
        (playingSongPosition / playingSong.duration).toFloat()
    } ?: 0f
}
