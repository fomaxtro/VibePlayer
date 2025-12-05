package com.fomaxtro.vibeplayer.core.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.fomaxtro.vibeplayer.core.database.dao.SongDao
import com.fomaxtro.vibeplayer.core.database.entity.SongEntity
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstSongRepository(
    private val songDao: SongDao,
    private val context: Context
) : SongRepository {
    override suspend fun scanSongs(minDurationSeconds: Long, minSize: Long) {
        return withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID
            )
            val selection = """
                "${MediaStore.Audio.Media.IS_MUSIC} = 1 AND
                ${MediaStore.Audio.Media.DURATION} >= ? AND
                ${MediaStore.Audio.Media.SIZE} >= ?
            """.trimIndent()
            val selectionArgs = arrayOf(
                minDurationSeconds.toString(),
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

                val artworkUriBase = Uri.parse("content://media/external/audio/albumart")

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
                            albumArtUri = albumArtUri.toString()
                        )
                    )
                }

                songDao.upsertAll(songs)
            }
        }
    }
}