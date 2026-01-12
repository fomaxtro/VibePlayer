package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long
}