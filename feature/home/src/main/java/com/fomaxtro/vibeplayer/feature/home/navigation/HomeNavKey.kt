package com.fomaxtro.vibeplayer.feature.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.home.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavKey : NavKey

fun EntryProviderScope<NavKey>.home(
    onScanMusic: () -> Unit,
    onSearch: () -> Unit,
    onPlaylistCreated: (playlistId: Long) -> Unit
) {
    entry<HomeNavKey> {
        HomeScreen(
            onScanMusic = onScanMusic,
            onSearch = onSearch,
            onPlaylistCreated = onPlaylistCreated
        )
    }
}