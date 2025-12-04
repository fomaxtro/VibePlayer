package com.fomaxtro.vibeplayer.core.database.di

import androidx.room.Room
import com.fomaxtro.vibeplayer.core.database.VibePlayerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val database = module {
    single<VibePlayerDatabase> {
        Room.databaseBuilder(
            androidContext(),
            VibePlayerDatabase::class.java,
            "vibeplayer"
        ).build()
    }
}