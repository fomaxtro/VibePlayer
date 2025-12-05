package com.fomaxtro.vibeplayer.core.database.di

import androidx.room.Room
import com.fomaxtro.vibeplayer.core.database.VibePlayerDatabase
import com.fomaxtro.vibeplayer.core.database.dao.SongDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    single<VibePlayerDatabase> {
        Room.databaseBuilder(
            androidContext(),
            VibePlayerDatabase::class.java,
            "vibeplayer"
        ).build()
    }
    single<SongDao> { get<VibePlayerDatabase>().songDao() }
}
