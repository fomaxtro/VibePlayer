package com.fomaxtro.vibeplayer.feature.library.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import kotlinx.serialization.Serializable

@Serializable
data object LibraryNavKey : NavKey

fun EntryProviderScope<NavKey>.library(
    onSongClick: (songIndex: Int) -> Unit,
    onScanAgain: () -> Unit,
) {
    entry<LibraryNavKey> {
        LibraryScreen(
            onSongClick = onSongClick,
            onScanAgain = onScanAgain
        )
    }
}