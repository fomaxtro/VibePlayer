package com.fomaxtro.vibeplayer.feature.home.di

import com.fomaxtro.vibeplayer.feature.home.home.HomeViewModel
import com.fomaxtro.vibeplayer.feature.player.di.featurePlayerModule
import com.fomaxtro.vibeplayer.feature.playlist.di.featurePlaylistModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureHomeModule = module {
    includes(
        featurePlayerModule,
        featurePlaylistModule
    )
    viewModelOf(::HomeViewModel)
}