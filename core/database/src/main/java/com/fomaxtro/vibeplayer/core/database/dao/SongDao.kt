package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Upsert
    suspend fun upsertAll(songs: List<SongEntity>)

    @Query("SELECT * FROM songs")
    fun getAll(): Flow<List<SongEntity>>
}