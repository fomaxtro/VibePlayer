package com.fomaxtro.vibeplayer.feature.player.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.player.player.PlayerScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlayerNavKey(val songIndex: Int) : NavKey

fun EntryProviderScope<NavKey>.player(
    onNavigateBack: () -> Unit
) {
    entry<PlayerNavKey> {
        PlayerScreen(
            songIndex = it.songIndex,
            onNavigateBack = onNavigateBack
        )
    }
}