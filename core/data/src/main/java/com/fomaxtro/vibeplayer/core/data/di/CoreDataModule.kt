package com.fomaxtro.vibeplayer.core.data.di

import com.fomaxtro.vibeplayer.core.data.repository.OfflineFirstPlaylistRepository
import com.fomaxtro.vibeplayer.core.data.repository.OfflineFirstSongRepository
import com.fomaxtro.vibeplayer.core.database.di.databaseModule
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module(createdAtStart = true) {
    includes(databaseModule)
    singleOf(::OfflineFirstSongRepository) bind SongRepository::class
    singleOf(::OfflineFirstPlaylistRepository) bind PlaylistRepository::class
}
