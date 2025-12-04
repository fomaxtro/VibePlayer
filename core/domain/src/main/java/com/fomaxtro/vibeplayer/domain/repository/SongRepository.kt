package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getSongsSteam(): Flow<List<Song>>
}