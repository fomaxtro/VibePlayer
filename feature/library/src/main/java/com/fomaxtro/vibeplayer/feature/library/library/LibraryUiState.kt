package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song

@Immutable
sealed interface LibraryUiState {
    data object Loading : LibraryUiState

    data class Success(
        val songs: List<Song> = emptyList()
    ) : LibraryUiState
}
