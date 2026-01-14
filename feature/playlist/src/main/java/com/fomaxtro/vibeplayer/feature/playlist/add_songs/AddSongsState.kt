package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.fomaxtro.vibeplayer.feature.playlist.add_songs.model.SelectableSong

@Immutable
sealed interface AddSongsState {
    data object Loading : AddSongsState

    data class Success(
        val search: TextFieldState,
        val songs: List<SelectableSong> = emptyList()
    ) : AddSongsState {
        val selectAll: Boolean = songs.isNotEmpty() && songs.all { it.selected }
        val canSubmit: Boolean = songs.any { it.selected }
    }
}