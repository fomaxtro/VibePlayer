package com.fomaxtro.vibeplayer.feature.scanner.scan_music

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScanMusicViewModel(
    minDurationSeconds: Long,
    minSize: Long
) : ViewModel() {
    private val _state = MutableStateFlow(ScanMusicState.Loading)
    val state = _state.asStateFlow()
}