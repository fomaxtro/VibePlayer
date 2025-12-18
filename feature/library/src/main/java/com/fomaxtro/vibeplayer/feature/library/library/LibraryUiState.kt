package com.fomaxtro.vibeplayer.feature.library.library

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song

@Immutable
data class LibraryUiState(
    val songs: List<Song> = emptyList(),
    val isSearching: Boolean = false,
    val query: String = ""
) {
    val isEmptyQuery: Boolean = isSearching && query.isBlank()
}
