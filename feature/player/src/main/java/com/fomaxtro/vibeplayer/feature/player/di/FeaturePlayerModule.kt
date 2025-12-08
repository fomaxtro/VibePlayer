package com.fomaxtro.vibeplayer.feature.player.di

import com.fomaxtro.vibeplayer.feature.player.player.PlayerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featurePlayerModule = module {
    viewModel<PlayerViewModel> { (songId: Long) ->
        PlayerViewModel(
            songId = songId,
            musicPlayer = get(),
            songRepository = get()
        )
    }
}