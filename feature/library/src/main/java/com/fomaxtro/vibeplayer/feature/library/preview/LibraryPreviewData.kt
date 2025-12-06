package com.fomaxtro.vibeplayer.feature.library.preview

import com.fomaxtro.vibeplayer.feature.library.model.SongUi

object LibraryPreviewData {
    fun createSong(
        id: Long
    ) = SongUi(
        id = id,
        title = "Song $id",
        artist = "Artist $id",
        duration = "03:45",
        albumArtUri = ""
    )
}