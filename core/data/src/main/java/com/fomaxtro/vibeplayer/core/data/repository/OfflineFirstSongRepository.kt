package com.fomaxtro.vibeplayer.core.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.room.withTransaction
import com.fomaxtro.vibeplayer.core.data.mapper.toSong
import com.fomaxtro.vibeplayer.core.database.VibePlayerDatabase
import com.fomaxtro.vibeplayer.core.database.dao.SongDao
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File

class OfflineFirstSongRepository(
    private val songDao: SongDao,
    private val context: Context,
    private val database: VibePlayerDatabase
) : SongRepository {
    private suspend fun getSongsQueryResult(
        minDurationSeconds: Int,
        minSize: Long
    ): List<SongEntity> {
        return withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.SIZE
            )
            val selection = """
                ${MediaStore.Audio.Media.IS_MUSIC} = 1 AND
                ${MediaStore.Audio.Media.DURATION} >= ? AND
                ${MediaStore.Audio.Media.SIZE} >= ?
            """.trimIndent()

            val selectionArgs = arrayOf(
                (minDurationSeconds * 1000L).toString(),
                minSize.toString()
            )

            val query = context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )

            query?.use { cursor ->
                val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val pathCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

                val artworkUriBase = "content://media/external/audio/albumart".toUri()

                val songs = mutableListOf<SongEntity>()

                while (cursor.moveToNext()) {
                    val albumId = cursor.getLong(albumIdCol)
                    val albumArtUri = ContentUris.withAppendedId(artworkUriBase, albumId)

                    songs.add(
                        SongEntity(
                            id = cursor.getLong(idCol),
                            title = cursor.getString(titleCol),
                            artist = cursor.getString(artistCol),
                            durationMillis = cursor.getLong(durationCol),
                            filePath = cursor.getString(pathCol),
                            albumArtUri = albumArtUri.toString(),
                            sizeBytes = cursor.getLong(sizeCol)
                        )
                    )
                }

                songs
            } ?: emptyList()
        }
    }

    override suspend fun scanSongs(minDurationSeconds: Int, minSize: Long): Int {
        val existingSongs = songDao.getAll().first()
        val newSongs = getSongsQueryResult(
            minDurationSeconds = minDurationSeconds,
            minSize = minSize
        )

        val newSongsId = newSongs.map { it.id }.toHashSet()
        val deleteSongs = existingSongs.filterNot { it.id in newSongsId }

        database.withTransaction {
            newSongs.chunked(1000).forEach { batch ->
                songDao.upsertAll(batch)
            }

            deleteSongs.chunked(1000).forEach { batch ->
                songDao.deleteAll(batch)
            }
        }

        return newSongs.size
    }

    override fun getSongsStream(): Flow<List<Song>> {
        return songDao.getAll()
            .map { songs ->
                songs.map { it.toSong() }
            }
    }

    override suspend fun syncLibrary() {
        val songs = songDao.getAll().first()
        val deleteSongs = withContext(Dispatchers.IO) {
            songs.filterNot { File(it.filePath).exists() }
        }

        songDao.deleteAll(deleteSongs)
    }
}