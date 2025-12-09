package com.fomaxtro.vibeplayer.feature.library.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import com.fomaxtro.vibeplayer.feature.library.scan_music.ScanMusicScreen
import kotlinx.serialization.Serializable

sealed interface LibraryRoute : NavKey {
    @Serializable
    data class Library(val shouldScan: Boolean = false) : LibraryRoute

    @Serializable
    data object ScanMusic : LibraryRoute
}

fun EntryProviderScope<NavKey>.library(
    onScanMusic: () -> Unit,
    onSongClick: (songIndex: Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    entry<LibraryRoute.Library> {
        LibraryScreen(
            autoScan = it.shouldScan,
            onScanMusic = onScanMusic,
            onSongClick = onSongClick
        )
    }

    entry<LibraryRoute.ScanMusic> {
        ScanMusicScreen(
            onNavigateBackClick = onNavigateBack
        )
    }
}