package com.fomaxtro.vibeplayer.feature.library.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import com.fomaxtro.vibeplayer.domain.use_case.ObserveSongs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModel(
    observeSongs: ObserveSongs,
    saveStateHandle: SavedStateHandle,
    private val player: MusicPlayer
) : ViewModel() {
    private companion object {
        const val QUERY = "QUERY"
        const val IS_SEARCHING = "IS_SEARCHING"
    }

    private val isSearching = saveStateHandle.getMutableStateFlow(IS_SEARCHING, false)
    private val query = saveStateHandle.getMutableStateFlow(QUERY, "")

    private val songs = observeSongs()
        .onEach { songs ->
            player.setPlaylist(songs)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    @OptIn(FlowPreview::class)
    private val filteredSongs = query
        .debounce(500)
        .distinctUntilChanged()
        .combine(songs) { query, songs ->
            if (query.isNotBlank()) {
                songs.filter {
                    it.title.contains(query, ignoreCase = true)
                            || it.artist.contains(query, ignoreCase = true)
                }
            } else emptyList()
        }

    @OptIn(FlowPreview::class)
    val state = combine(
        songs,
        isSearching,
        query,
        filteredSongs
    ) { songs, isSearching, query, filteredSongs ->
        LibraryUiState(
            songs = if (isSearching) filteredSongs else songs,
            isSearching = isSearching,
            query = query
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LibraryUiState()
        )

    fun onAction(action: LibraryAction) {
        when (action) {
            else -> Unit
        }
    }

    override fun onCleared() {
        super.onCleared()

        player.clearPlaylist()
    }
}