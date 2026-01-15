package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import com.fomaxtro.vibeplayer.feature.playlist.add_songs.model.SelectableSong

sealed interface AddSongsAction {
    data object OnSelectAllToggle : AddSongsAction
    data class OnSongClick(val song: SelectableSong) : AddSongsAction
    data object OnSubmitClick : AddSongsAction
    data object OnNavigateBackClick : AddSongsAction
}