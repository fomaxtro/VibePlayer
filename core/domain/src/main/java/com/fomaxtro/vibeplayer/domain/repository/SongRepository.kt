package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    suspend fun scanSongs(minDurationSeconds: Long = 0, minSize: Long = 0)
    fun getSongsStream(): Flow<List<Song>>
}