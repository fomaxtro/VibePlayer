package com.fomaxtro.vibeplayer.feature.home.model

import androidx.annotation.StringRes
import com.fomaxtro.vibeplayer.feature.home.R

enum class Destination(
    @param:StringRes val labelRes: Int
) {
    SONGS(R.string.songs),
    PLAYLIST(R.string.playlist)
}