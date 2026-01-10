package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity

@Dao
interface PlaylistDao {
    @Upsert
    suspend fun upsertPlaylist(playlist: PlaylistEntity): Long
}