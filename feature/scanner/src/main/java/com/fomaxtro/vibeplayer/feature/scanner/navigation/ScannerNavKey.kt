package com.fomaxtro.vibeplayer.feature.scanner.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.scanner.permission.PermissionScreen
import com.fomaxtro.vibeplayer.feature.scanner.scan_music.ScanMusicScreen
import kotlinx.serialization.Serializable

@Serializable
data object Scanner : NavKey

@Serializable
data class ScanMusic(
    val minDurationSeconds: Long = 0,
    val minSize: Long = 0
) : NavKey

fun EntryProviderScope<NavKey>.scanner() {
    entry<Scanner> {
        PermissionScreen(
            onPermissionGranted = {}
        )
    }

    entry<ScanMusic> {
        ScanMusicScreen(
            minDurationSeconds = it.minDurationSeconds,
            minSize = it.minSize
        )
    }
}