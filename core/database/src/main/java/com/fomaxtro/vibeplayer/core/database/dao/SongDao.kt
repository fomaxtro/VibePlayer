package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Upsert
    suspend fun upsertAll(songs: List<SongEntity>)

    @Update
    suspend fun updateSong(song: SongEntity)

    @Query("SELECT * FROM songs")
    fun getAllStream(): Flow<List<SongEntity>>

    @Delete
    suspend fun deleteAll(songs: List<SongEntity>)

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun findById(id: Long): SongEntity?

    @Query("SELECT * FROM songs WHERE id IN (:ids)")
    fun findByIdsStream(ids: List<Long>): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE title LIKE '%' || :query || '%' OR artist LIKE '%' || :query || '%'")
    suspend fun findByTitleOrArtist(query: String): List<SongEntity>

    @Query("SELECT COUNT(*) FROM songs WHERE is_favourite = true")
    fun getFavouriteSongsCountStream(): Flow<Int>
}