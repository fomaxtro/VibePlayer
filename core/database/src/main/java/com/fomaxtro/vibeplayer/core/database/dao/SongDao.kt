package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity

@Dao
interface SongDao {
    @Upsert
    suspend fun upsertAll(songs: List<SongEntity>)
}