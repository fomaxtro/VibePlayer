package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.core.common.EmptyResult
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist

interface PlaylistRepository {
    suspend fun createPlaylist(playlist: NewPlaylist): Result<Long, DataError>
    suspend fun addSongsToPlaylist(playlistId: Long, songIds: List<Long>): EmptyResult<DataError>
}