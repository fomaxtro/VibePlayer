package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.fomaxtro.vibeplayer.core.ui.preview.SongPreviewFactory
import com.fomaxtro.vibeplayer.feature.playlist.add_songs.model.SelectableSong

class AddSongsPreviewParameterProvider : PreviewParameterProvider<AddSongsState> {
    override val values: Sequence<AddSongsState> = sequenceOf(
        AddSongsState.Loading,
        AddSongsState.Success(
            search = TextFieldState(),
            songs = emptyList()
        ),
        AddSongsState.Success(
            search = TextFieldState(),
            songs = SongPreviewFactory.defaultList.map { song ->
                SelectableSong(
                    song = song,
                    selected = true
                )
            }
        ),
        AddSongsState.Success(
            search = TextFieldState(),
            songs = SongPreviewFactory.defaultList.map { song ->
                SelectableSong(
                    song = song,
                    selected = false
                )
            }
        )
    )

    override fun getDisplayName(index: Int): String? {
        return when (index) {
            0 -> "Loading"
            1 -> "Empty List"
            2 -> "All Selected"
            3 -> "All Unselected"
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
    }
}