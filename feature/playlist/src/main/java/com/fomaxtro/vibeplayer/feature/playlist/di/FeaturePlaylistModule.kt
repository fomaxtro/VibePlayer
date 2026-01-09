package com.fomaxtro.vibeplayer.feature.playlist.di

import com.fomaxtro.vibeplayer.feature.playlist.playlist.PlaylistViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featurePlaylistModule = module {
    viewModelOf(::PlaylistViewModel)
}