package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistSongCrossRef

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlaylistSongs(crossRef: List<PlaylistSongCrossRef>)
}