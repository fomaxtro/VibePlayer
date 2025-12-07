package com.fomaxtro.vibeplayer.domain.di

import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreDomainModule = module {
    factoryOf(::ObserveSongs)
}