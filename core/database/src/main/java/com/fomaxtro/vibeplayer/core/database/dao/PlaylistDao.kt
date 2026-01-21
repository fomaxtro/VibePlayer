package com.fomaxtro.vibeplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistEntity
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistSongCrossRef
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistWithMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insert(playlist: PlaylistEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSongs(crossRef: List<PlaylistSongCrossRef>)

    @Query("""
        SELECT
            p.id,
            p.name,
            (
                SELECT COUNT(*)
                FROM playlist_songs ps
                WHERE ps.playlist_id = p.id
            ) AS song_count,
            (
                SELECT s.album_art_uri
                FROM songs s
                INNER JOIN playlist_songs ps ON ps.song_id = s.id
                WHERE ps.playlist_id = p.id
                LIMIT 1
            ) AS album_art_uri
        FROM playlists p
    """)
    fun getPlaylistsWithMetadataStream(): Flow<List<PlaylistWithMetadata>>
}