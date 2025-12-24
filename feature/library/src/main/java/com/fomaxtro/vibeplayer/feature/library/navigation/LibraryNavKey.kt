package com.fomaxtro.vibeplayer.feature.library.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchNavKey : NavKey

fun EntryProviderScope<NavKey>.library(
    onCancel: () -> Unit,
    onPlaySong: () -> Unit
) {
    entry<SearchNavKey> {
        SearchScreen(
            onCancel = onCancel,
            onPlaySong = onPlaySong
        )
    }
}