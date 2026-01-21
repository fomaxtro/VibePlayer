package com.fomaxtro.vibeplayer.feature.library.search

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.ui.util.Resource
import com.fomaxtro.vibeplayer.core.ui.util.getTextFieldState
import com.fomaxtro.vibeplayer.domain.model.Song
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    songRepository: SongRepository,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private companion object {
        const val SEARCH_QUERY_KEY = "search_query"
    }

    private val eventChannel = Channel<SearchEvent>()
    val events = eventChannel.receiveAsFlow()

    private val playlist = musicPlayer.playerState
        .map { it.playlist }
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    private val searchQuery = savedStateHandle.getTextFieldState(
        scope = viewModelScope,
        key = SEARCH_QUERY_KEY,
        initialValue = ""
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val filteredSongs = snapshotFlow { searchQuery.text.toString() }
        .debounce { searchQuery ->
            if (searchQuery.isNotEmpty()) 500L else 0L
        }
        .distinctUntilChanged()
        .transformLatest { searchQuery ->
            if (searchQuery.isEmpty()) {
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Loading)
                emit(
                    Resource.Success(
                        songRepository.findSongsByTitleOrArtist(searchQuery)
                    )
                )
            }
        }

    val state = filteredSongs
        .map { filteredSongs ->
            SearchUiState(
                searchQuery = searchQuery,
                songs = filteredSongs
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SearchUiState()
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSongClick -> onSongClick(action.song)
            else -> Unit
        }
    }

    private fun onSongClick(song: Song) = viewModelScope.launch {
        val songIndex = playlist.value.indexOfFirst { it.id == song.id }

        if (songIndex != -1) {
            musicPlayer.play(songIndex)
            eventChannel.send(SearchEvent.PlaySong)
        }
    }
}