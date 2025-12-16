package com.fomaxtro.vibeplayer.app.di

import com.fomaxtro.vibeplayer.app.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}