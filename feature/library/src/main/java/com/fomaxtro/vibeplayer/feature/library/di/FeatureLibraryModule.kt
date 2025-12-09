package com.fomaxtro.vibeplayer.feature.library.di

import com.fomaxtro.vibeplayer.feature.library.library.LibraryViewModel
import com.fomaxtro.vibeplayer.feature.library.scan_music.ScanMusicViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLibraryModule = module {
    viewModel<LibraryViewModel> { (autoScan: Boolean) ->
        LibraryViewModel(
            autoScan = autoScan,
            observeSongs = get(),
            songRepository = get(),
            player = get()
        )
    }
    viewModelOf(::ScanMusicViewModel)
}