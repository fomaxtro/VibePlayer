package com.fomaxtro.vibeplayer.feature.library.di

import com.fomaxtro.vibeplayer.feature.library.library.LibraryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLibraryModule = module {
    viewModelOf(::LibraryViewModel)
}