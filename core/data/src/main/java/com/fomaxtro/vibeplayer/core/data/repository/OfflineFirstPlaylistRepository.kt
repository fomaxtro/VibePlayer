package com.fomaxtro.vibeplayer.core.data.repository

import com.fomaxtro.vibeplayer.core.common.EmptyResult
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.data.mapper.toDomain
import com.fomaxtro.vibeplayer.core.data.mapper.toEntity
import com.fomaxtro.vibeplayer.core.database.dao.PlaylistDao
import com.fomaxtro.vibeplayer.core.database.entity.PlaylistSongCrossRef
import com.fomaxtro.vibeplayer.core.database.util.safeDatabaseCall
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.model.Playlist
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstPlaylistRepository(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {
    override suspend fun createPlaylist(playlist: NewPlaylist): Result<Long, DataError> {
        return safeDatabaseCall { playlistDao.insert(playlist.toEntity()) }
    }

    override suspend fun addSongsToPlaylist(
        playlistId: Long,
        songIds: List<Long>
    ): EmptyResult<DataError> {
        val crossRefs = songIds.map { songId ->
            PlaylistSongCrossRef(
                playlistId = playlistId,
                songId = songId
            )
        }

        return safeDatabaseCall { playlistDao.addSongs(crossRefs) }
    }

    override fun getPlaylistsStream(): Flow<List<Playlist>> {
        return playlistDao.getPlaylistsWithMetadataStream()
            .map { it.toDomain() }
    }
}