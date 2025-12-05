package com.fomaxtro.vibeplayer.feature.library.mapper

import com.fomaxtro.vibeplayer.core.ui.util.formatDuration
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.feature.library.model.SongUi

fun Song.toSongUi() = SongUi(
    id = id,
    title = title,
    artist = artist,
    duration = durationMillis.formatDuration(),
    albumArtUri = albumArtUri
)