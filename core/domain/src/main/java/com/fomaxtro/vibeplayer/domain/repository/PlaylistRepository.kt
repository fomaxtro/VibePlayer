package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.core.common.EmptyResult
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist
import com.fomaxtro.vibeplayer.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun createPlaylist(playlist: NewPlaylist): Result<Long, DataError>
    suspend fun addSongsToPlaylist(playlistId: Long, songIds: List<Long>): EmptyResult<DataError>
    fun getPlaylistsStream(): Flow<List<Playlist>>
}