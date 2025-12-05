package com.fomaxtro.vibeplayer.feature.scanner.di

import com.fomaxtro.vibeplayer.feature.scanner.scan_music.ScanMusicViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val scannerModule = module {
    viewModel<ScanMusicViewModel> { (minDurationSeconds: Long, minSize: Long) ->
        ScanMusicViewModel(
            minDurationSeconds = minDurationSeconds,
            minSize = minSize
        )
    }
}