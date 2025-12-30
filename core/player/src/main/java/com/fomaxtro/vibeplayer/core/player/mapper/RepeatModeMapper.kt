package com.fomaxtro.vibeplayer.core.player.mapper

import androidx.media3.common.Player
import com.fomaxtro.vibeplayer.domain.player.RepeatMode

fun RepeatMode.toExoPlayerRepeatMode(): Int {
    return when (this) {
        RepeatMode.OFF -> Player.REPEAT_MODE_OFF
        RepeatMode.ONE -> Player.REPEAT_MODE_ONE
        RepeatMode.ALL -> Player.REPEAT_MODE_ALL
    }
}

fun Int.toRepeatMode(): RepeatMode {
    return when (this) {
        Player.REPEAT_MODE_OFF -> RepeatMode.OFF
        Player.REPEAT_MODE_ONE -> RepeatMode.ONE
        Player.REPEAT_MODE_ALL -> RepeatMode.ALL
        else -> throw IllegalArgumentException("Invalid repeat mode: $this")
    }
}