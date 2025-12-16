package com.fomaxtro.vibeplayer.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val songRepository: SongRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = MainUiState.Success(
                hasSongs = songRepository.getSongsStream().first().isNotEmpty()
            )
        }
    }
}