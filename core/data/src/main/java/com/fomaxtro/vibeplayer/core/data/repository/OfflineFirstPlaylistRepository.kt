package com.fomaxtro.vibeplayer.core.data.repository

import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.data.mapper.toEntity
import com.fomaxtro.vibeplayer.core.data.util.safeDatabaseCall
import com.fomaxtro.vibeplayer.core.database.dao.PlaylistDao
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository

class OfflineFirstPlaylistRepository(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {
    override suspend fun createPlaylist(playlist: NewPlaylist): Result<Long, DataError> {
        return safeDatabaseCall { playlistDao.upsertPlaylist(playlist.toEntity()) }
    }
}