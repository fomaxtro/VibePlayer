package com.fomaxtro.vibeplayer.domain.repository

interface SongRepository {
    suspend fun scanSongs(minDurationSeconds: Long = 0, minSize: Long = 0)
}