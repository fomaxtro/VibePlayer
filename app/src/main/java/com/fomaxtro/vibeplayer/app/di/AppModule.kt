package com.fomaxtro.vibeplayer.app.di

import com.fomaxtro.vibeplayer.app.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    single<CoroutineScope> { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
}