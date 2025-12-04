package com.fomaxtro.vibeplayer.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fomaxtro.vibeplayer.core.database.dao.SongDao
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity

@Database(
    entities = [
        SongEntity::class
    ],
    version = 1
)
abstract class VibePlayerDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}