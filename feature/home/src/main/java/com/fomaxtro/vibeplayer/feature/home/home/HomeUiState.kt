package com.fomaxtro.vibeplayer.feature.home.home

import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.domain.model.Song

@Immutable
data class HomeUiState(
    val songs: List<Song> = emptyList(),
    val isPlayerExpanded: Boolean = false
)
