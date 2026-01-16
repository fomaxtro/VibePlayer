package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.feature.playlist.add_songs.model.SelectableSong

@Immutable
sealed interface AddSongsUiState {
    data object Loading : AddSongsUiState

    data class Success(
        val search: TextFieldState,
        val songs: List<SelectableSong> = emptyList(),
        val selectedSongsCount: Int = 0,
        val isSelectedAll: Boolean = false,
        val canSubmit: Boolean = false
    ) : AddSongsUiState
}