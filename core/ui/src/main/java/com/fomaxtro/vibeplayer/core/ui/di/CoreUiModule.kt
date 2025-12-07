package com.fomaxtro.vibeplayer.core.ui.di

import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreUiModule = module {
    singleOf(::SnackbarController)
}