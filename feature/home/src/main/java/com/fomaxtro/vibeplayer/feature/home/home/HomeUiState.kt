package com.fomaxtro.vibeplayer.feature.home.home

import com.fomaxtro.vibeplayer.domain.model.Song

data class HomeUiState(
    val songs: List<Song> = emptyList(),
    val isPlayerExpanded: Boolean = false
)
