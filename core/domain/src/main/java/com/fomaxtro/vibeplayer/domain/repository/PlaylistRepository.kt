package com.fomaxtro.vibeplayer.domain.repository

import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.domain.error.DataError
import com.fomaxtro.vibeplayer.domain.model.NewPlaylist

interface PlaylistRepository {
    suspend fun createPlaylist(playlist: NewPlaylist): Result<Long, DataError>
}