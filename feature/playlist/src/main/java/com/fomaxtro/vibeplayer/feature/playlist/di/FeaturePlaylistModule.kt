package com.fomaxtro.vibeplayer.feature.playlist.di

import com.fomaxtro.vibeplayer.feature.playlist.add_songs.AddSongsViewModel
import com.fomaxtro.vibeplayer.feature.playlist.playlist.PlaylistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featurePlaylistModule = module {
    viewModelOf(::PlaylistViewModel)
    viewModel<AddSongsViewModel> { (playlistId: Long) ->
        AddSongsViewModel(
            playlistId = playlistId,
            applicationScope = get(),
            playlistRepository = get(),
            songRepository = get(),
            savedStateHandle = get(),
            snackbarController = get()
        )
    }
}