package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    fun getAll(): Flow<List<SongEntity>>
}