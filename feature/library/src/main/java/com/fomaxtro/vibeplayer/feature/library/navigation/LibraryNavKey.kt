package com.fomaxtro.vibeplayer.feature.library.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchNavKey : NavKey

fun EntryProviderScope<NavKey>.library(
    onCancelClick: () -> Unit,
    onPlaysongClick: () -> Unit
) {
    entry<SearchNavKey> {
        SearchScreen(
            onCancelClick = onCancelClick,
            onPlaysongClick = onPlaysongClick
        )
    }
}