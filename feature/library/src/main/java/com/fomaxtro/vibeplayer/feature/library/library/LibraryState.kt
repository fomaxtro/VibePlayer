package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.feature.library.model.SongUi

@Immutable
sealed interface LibraryState {
    data object Loading : LibraryState

    data class Success(
        val songs: List<SongUi> = emptyList()
    ) : LibraryState

    data object Empty : LibraryState
}
