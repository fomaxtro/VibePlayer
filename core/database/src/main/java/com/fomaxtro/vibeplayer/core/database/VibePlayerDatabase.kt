package com.fomaxtro.vibeplayer.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.fomaxtro.vibeplayer.core.database.dao.SongDao
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistSongCrossRef
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity

@Database(
    entities = [
        SongEntity::class,
        PlaylistEntity::class,
        PlaylistSongCrossRef::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class VibePlayerDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}