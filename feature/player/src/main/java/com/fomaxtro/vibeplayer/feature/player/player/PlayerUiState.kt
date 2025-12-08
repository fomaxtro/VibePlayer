package com.fomaxtro.vibeplayer.feature.player.player

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song

@Immutable
sealed interface PlayerUiState {
    data object Loading : PlayerUiState

    data class Success(
        val song: Song
    ) : PlayerUiState
}
