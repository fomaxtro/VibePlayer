package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.core.ui.mapper.toUiText
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.UiText
import com.fomaxtro.vibeplayer.domain.repository.SongRepository
import com.fomaxtro.vibeplayer.feature.scanner.R
import com.fomaxtro.vibeplayer.feature.scanner.model.DurationConstraint
import com.fomaxtro.vibeplayer.feature.scanner.model.SizeConstraint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanOptionsViewModel(
    private val songRepository: SongRepository,
    private val snackbarController: SnackbarController
) : ViewModel() {
    private val eventChannel = Channel<ScanOptionsEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ScanOptionsUiState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanOptionsAction) {
        when (action) {
            is ScanOptionsAction.OnDurationConstraintSelected -> {
                onDurationConstraintSelected(action.durationConstraint)
            }

            ScanOptionsAction.OnScanClick -> onScanClick()

            is ScanOptionsAction.OnSizeConstraintSelected -> {
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
                        ScanOptionsEvent.ShowMessage(
                            message = soundsCount.error.toUiText()
                        )
                    )
                }

                is Result.Success -> {
                    eventChannel.send(ScanOptionsEvent.NavigateBack)

                    snackbarController.showSnackbar(
                        message = UiText.StringResource(
                            resId = R.string.scan_complete,
                            args = listOf(soundsCount.data)
                        )
                    )
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