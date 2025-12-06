package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.feature.library.model.SongUi

@Immutable
sealed interface LibraryUiState {
    data object Loading : LibraryUiState

    data class Success(
        val songs: List<SongUi> = emptyList()
    ) : LibraryUiState

    data object Empty : LibraryUiState
}
