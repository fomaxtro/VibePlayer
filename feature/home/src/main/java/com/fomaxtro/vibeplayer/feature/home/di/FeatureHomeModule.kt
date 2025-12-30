package com.fomaxtro.vibeplayer.feature.home.di

import com.fomaxtro.vibeplayer.feature.home.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureHomeModule = module {
    viewModelOf(::HomeViewModel)
}