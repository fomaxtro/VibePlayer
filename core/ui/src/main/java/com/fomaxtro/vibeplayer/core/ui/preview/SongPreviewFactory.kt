package com.fomaxtro.vibeplayer.core.ui.preview

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

object SongPreviewFactory {
    val defaultList: List<Song> = List(10) { index ->
        makeSong(
            id = index.toLong(),
            title = "Track ${index + 1}",
            artist = if (index % 2 == 0) "Daft Punk" else "The Weeknd"
        )
    }

    fun makeSong(
        id: Long = 1,
        title: String = "Midnight City",
        artist: String = "M83",
        duration: Duration = 4.minutes,
        isFavourite: Boolean = false
    ): Song {
        return Song(
            id = id,
            title = title,
            artist = artist,
            duration = duration,
            albumArtUri = null,
            filePath = "/storage/emulated/0/Music/song_$id.mp3",
            sizeBytes = 1024 * 1024 * 5,
            isFavourite = isFavourite
        )
    }
}