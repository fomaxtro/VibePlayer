package com.fomaxtro.vibeplayer.feature.player.di

import com.fomaxtro.vibeplayer.feature.player.player.PlayerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featurePlayerModule = module {
    viewModelOf(::PlayerViewModel)
}