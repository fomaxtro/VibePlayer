package com.fomaxtro.vibeplayer.feature.library.scan_music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanMusicViewModel(
    private val songRepository: SongRepository,
    private val snackbarController: SnackbarController
) : ViewModel() {
    private val eventChannel = Channel<ScanMusicEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ScanMusicUiState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanMusicAction) {
        when (action) {
            is ScanMusicAction.OnDurationConstraintSelected -> {
                onDurationConstraintSelected(action.durationConstraint)
            }

            ScanMusicAction.OnScanClick -> onScanClick()

            is ScanMusicAction.OnSizeConstraintSelected -> {
                onSizeConstraintSelected(action.sizeConstraint)
            }
            else -> Unit
        }
    }

    private fun onScanClick() = viewModelScope.launch {
        _state.update { it.copy(isScanning = true) }

        try {
            when (
                val soundsCount = with(_state.value) {
                    songRepository.scanSongs(
                        minDurationSeconds = selectedDurationConstraint.durationSeconds,
                        minSize = selectedSizeConstraint.size,
                    )
                }
            ) {
                is Result.Error -> {
                    eventChannel.send(
                        ScanMusicEvent.ShowMessage(
                            message = soundsCount.error.toUiText()
                        )
                    )
                }

                is Result.Success -> {
                    snackbarController.showSnackbar(
                        message = UiText.StringResource(
                            resId = R.string.scan_complete,
                            args = listOf(soundsCount.data)
                        )
                    )

                    eventChannel.send(ScanMusicEvent.NavigateBack)
                }
            }
        } finally {
            _state.update { it.copy(isScanning = false) }
        }
    }

    private fun onSizeConstraintSelected(sizeConstraint: SizeConstraint) {
        _state.update {
            it.copy(selectedSizeConstraint = sizeConstraint)
        }
    }

    private fun onDurationConstraintSelected(durationConstraint: DurationConstraint) {
        _state.update {
            it.copy(selectedDurationConstraint = durationConstraint)
        }
    }
}