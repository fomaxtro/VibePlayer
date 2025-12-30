package com.fomaxtro.vibeplayer.feature.library.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.core.ui.util.Resource
import com.fomaxtro.vibeplayer.domain.model.Song

@Immutable
data class SearchUiState(
    val searchQuery: TextFieldState = TextFieldState(),
    val songs: Resource<List<Song>> = Resource.Success(emptyList())
)
