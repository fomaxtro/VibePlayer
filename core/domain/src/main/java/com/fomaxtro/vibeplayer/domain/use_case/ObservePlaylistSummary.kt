package com.fomaxtro.vibeplayer.domain.use_case

import com.fomaxtro.vibeplayer.domain.model.PlaylistSummary
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObservePlaylistSummary(
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) {
    operator fun invoke(): Flow<PlaylistSummary> {
        return combine(
            playlistRepository.getPlaylistsStream(),
            songRepository.getFavouriteSongsCountStream()
        ) { playlists, favouriteSongsCount ->
            PlaylistSummary(
                playlists = playlists,
                favouriteSongsCount = favouriteSongsCount
            )
        }
    }
}