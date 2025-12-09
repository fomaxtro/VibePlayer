package com.fomaxtro.vibeplayer.feature.scanner.di

import com.fomaxtro.vibeplayer.feature.scanner.scan_options.ScanOptionsViewModel
import com.fomaxtro.vibeplayer.feature.scanner.scan_progress.ScanProgressViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureScannerModule = module {
    viewModelOf(::ScanProgressViewModel)
    viewModelOf(::ScanOptionsViewModel)
}