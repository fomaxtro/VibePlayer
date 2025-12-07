package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    /**
     * Scans the device's storage for audio files and adds them to the library.
     *
     * This function searches for music files and filters them based on minimum duration and size.
     * It then populates the local database with the found songs.
     *
     * @param minDurationSeconds The minimum duration in seconds for a song to be included. Defaults to 0.
     * @param minSize The minimum file size in bytes for a song to be included. Defaults to 0.
     * @return The total number of new songs found and added to the library.
     */
    suspend fun scanSongs(minDurationSeconds: Int = 0, minSize: Long = 0): Int
    fun getSongsStream(): Flow<List<Song>>
    suspend fun syncLibrary()
}