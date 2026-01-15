package com.fomaxtro.vibeplayer.core.ui

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun <T : NavKey> NavBackStack<NavKey>.addSafe(destination: T) {
    if (lastOrNull() != destination) {
        add(destination)
    }
}