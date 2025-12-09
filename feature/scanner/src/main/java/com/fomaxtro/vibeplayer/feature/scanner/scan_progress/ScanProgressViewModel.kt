package com.fomaxtro.vibeplayer.feature.scanner.scan_progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScanProgressViewModel(
    songRepository: SongRepository
) : ViewModel() {
    private val eventChannel = Channel<ScanProgressEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            songRepository.scanSongs()
            eventChannel.send(ScanProgressEvent.ScanFinish)
        }
    }
}