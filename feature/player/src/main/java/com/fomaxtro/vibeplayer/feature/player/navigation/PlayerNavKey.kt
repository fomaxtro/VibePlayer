package com.fomaxtro.vibeplayer.feature.player.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.player.player.PlayerScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlayerNavKey(val songId: Long) : NavKey

fun EntryProviderScope<NavKey>.player(
    backStack: NavBackStack<NavKey>
) {
    entry<PlayerNavKey> {
        PlayerScreen(
            songId = it.songId,
            onNavigateBack = {
                backStack.removeLastOrNull()
            }
        )
    }
}