package com.fomaxtro.vibeplayer.feature.library.di

import com.fomaxtro.vibeplayer.feature.library.library.LibraryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val libraryModule = module {
    viewModel<LibraryViewModel> { (autoScan: Boolean) ->
        LibraryViewModel(
            autoScan = autoScan,
            observeSongs = get(),
            songRepository = get()
        )
    }
}