package com.fomaxtro.vibeplayer.domain.use_case

import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ObserveSongs(
    private val songRepository: SongRepository
) {
    operator fun invoke(
        scope: CoroutineScope
    ): Flow<List<Song>> {
        return songRepository.getSongsStream()
            .onStart {
                scope.launch(Dispatchers.IO) {
                    songRepository.syncLibrary()
                }
            }
    }
}