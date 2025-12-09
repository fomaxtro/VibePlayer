package com.fomaxtro.vibeplayer.core.ui.util

import kotlin.time.Duration

fun Duration.formatDuration(): String {
    return toComponents { minutes, seconds, _ ->
        val minutesString = minutes.toString().padStart(2, '0')
        val secondsString = seconds.toString().padStart(2, '0')

        "$minutesString:$secondsString"
    }
}