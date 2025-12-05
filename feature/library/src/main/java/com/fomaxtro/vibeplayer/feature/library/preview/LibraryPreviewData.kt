package com.fomaxtro.vibeplayer.feature.library.preview

import com.fomaxtro.vibeplayer.feature.library.model.SongUi

object LibraryPreviewData {
    val songs = listOf(
        SongUi(
            id = 1,
            title = "Song 1",
            artist = "Artist 1",
            duration = "03:45",
            albumArtUri = ""
        ),
        SongUi(
            id = 2,
            title = "Song 2",
            artist = "Artist 2",
            duration = "04:20",
            albumArtUri = ""
        ),
        SongUi(
            id = 3,
            title = "Song 3",
            artist = "Artist 3",
            duration = "02:15",
            albumArtUri = ""
        )
    )
}