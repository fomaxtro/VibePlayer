package com.fomaxtro.vibeplayer.feature.playlist.add_songs

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.core.ui.util.getTextFieldState
import com.fomaxtro.vibeplayer.domain.repository.PlaylistRepository
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.feature.playlist.R
import com.fomaxtro.vibeplayer.feature.playlist.add_songs.model.SelectableSong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddSongsViewModel(
    private val playlistId: Long,
    private val applicationScope: CoroutineScope,
    private val playlistRepository: PlaylistRepository,
    private val snackbarController: SnackbarController,
    songRepository: SongRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private companion object Companion {
        const val SELECTED_SONGS_KEY = "selected_songs"
        const val SEARCH_KEY = "search"
    }

    private val eventChannel = Channel<AddSongsEvent>()
    val events = eventChannel.receiveAsFlow()

    private val selectedSongsIds = savedStateHandle.getMutableStateFlow(
        key = SELECTED_SONGS_KEY,
        initialValue = LongArray(0)
    )
    private val search = savedStateHandle.getTextFieldState(
        scope = viewModelScope,
        key = SEARCH_KEY,
        initialValue = ""
    )

    private val selectableSongs = songRepository.getSongsStream()
        .combine(selectedSongsIds) { songs, selectedIds ->
            songs.map { song ->
                SelectableSong(
                    song = song,
                    selected = selectedIds.contains(song.id)
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    @OptIn(FlowPreview::class)
    private val filteredSongs = snapshotFlow { search.text.toString() }
        .debounce(500)
        .distinctUntilChanged()
        .combine(selectableSongs) { search, selectableSongs ->
            if (search.isBlank()) {
                selectableSongs
            } else {
                selectableSongs.filter { selectableSong ->
                    val song = selectableSong.song

                    song.title.contains(search, ignoreCase = true)
                            || song.artist.contains(search, ignoreCase = true)
                }
            }
        }

    val state = combine(
        selectableSongs,
        filteredSongs
    ) { selectableSongs, filteredSongs ->
        AddSongsUiState.Success(
            search = search,
            songs = filteredSongs,
            selectedSongsCount = selectableSongs.count { it.selected },
            isSelectedAll = isSelectedAll(selectableSongs),
            canSubmit = selectableSongs.any { it.selected },
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AddSongsUiState.Loading
        )

    private fun isSelectedAll(selectableSongs: List<SelectableSong>): Boolean =
        selectableSongs.isNotEmpty() && selectableSongs.all { it.selected }

    fun onAction(action: AddSongsAction) {
        when (action) {
            AddSongsAction.OnSelectAllToggle -> toggleAll()
            is AddSongsAction.OnSongClick -> toggleSong(action.song)
            AddSongsAction.OnSubmitClick -> submit()
            else -> Unit
        }
    }

    private fun submit() {
        applicationScope.launch {
            playlistRepository.addSongsToPlaylist(
                playlistId = playlistId,
                songIds = selectedSongsIds.value.toList()
            )
        }

        viewModelScope.launch {
            eventChannel.send(AddSongsEvent.PlaylistCreated)
            snackbarController.showSnackbar(
                message = UiText.StringResource(
                    resId = R.string.songs_added_to_playlist,
                    args = listOf(selectedSongsIds.value.size)
                )
            )
        }
    }

    private fun toggleSong(song: SelectableSong) {
        val newSelectableSongs = selectableSongs.value.toMutableList()
        val index = newSelectableSongs.indexOf(song)

        if (index != -1) {
            newSelectableSongs[index] = song.copy(
                selected = !song.selected
            )

            selectedSongsIds.value = newSelectableSongs
                .filter { it.selected }
                .map { it.song.id }
                .toLongArray()
        }
    }

    private fun toggleAll() {
        if (isSelectedAll(selectableSongs.value)) {
            selectedSongsIds.update { LongArray(0) }
        } else {
            selectedSongsIds.value = selectableSongs.value
                .map { it.song.id }
                .toLongArray()
        }
    }
}