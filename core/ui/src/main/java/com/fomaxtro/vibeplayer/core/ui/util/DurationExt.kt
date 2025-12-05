package com.fomaxtro.vibeplayer.core.ui.util

import kotlin.time.Duration.Companion.milliseconds

fun Long.formatDuration(): String {
    return this.milliseconds.toComponents { minutes, seconds, _ ->
        val minutesString = minutes.toString().padStart(2, '0')
        val secondsString = seconds.toString().padStart(2, '0')

        "$minutesString:$secondsString"
    }
}