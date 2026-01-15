package com.fomaxtro.vibeplayer.feature.playlist.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.playlist.add_songs.AddSongsScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class AddSongsNavKey(val playlistId: Long) : NavKey

fun EntryProviderScope<NavKey>.playlist(
    onNavigateBack: () -> Unit
) {
    entry<AddSongsNavKey> {
        AddSongsScreen(
            onNavigateBack = onNavigateBack,
            viewModel = koinViewModel {
                parametersOf(it.playlistId)
            }
        )
    }
}