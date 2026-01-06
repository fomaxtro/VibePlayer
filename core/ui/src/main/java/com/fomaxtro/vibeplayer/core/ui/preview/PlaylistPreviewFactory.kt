package com.fomaxtro.vibeplayer.core.ui.preview

import com.fomaxtro.vibeplayer.domain.model.Playlist
import kotlin.random.Random

object PlaylistPreviewFactory {
    val defaultList: List<Playlist> = List(10) {
        makePlaylist(id = it.toLong())
    }

    fun makePlaylist(
        id: Long = 1,
        name: String = "Playlist $id"
    ) = Playlist(
        id = id,
        name = name,
        songsCount = Random.nextInt(0, 10),
        albumArtUri = null
    )
}