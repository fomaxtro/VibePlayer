package com.fomaxtro.vibeplayer.feature.scanner.scan_options

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanOptionsViewModel(
    private val songRepository: SongRepository,
    private val snackbarController: SnackbarController,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private companion object {
        const val DURATION_CONSTRAINT_KEY = "DURATION_CONSTRAINT"
        const val SIZE_CONSTRAINT_KEY = "SIZE_CONSTRAINT"
    }

    private val durationConstraint = savedStateHandle.getMutableStateFlow(
        key = DURATION_CONSTRAINT_KEY,
        initialValue = DurationConstraint.THIRTY_SECONDS
    )
    private val sizeConstraint = savedStateHandle.getMutableStateFlow(
        key = SIZE_CONSTRAINT_KEY,
        initialValue = SizeConstraint.ONE_HUNDRED_KB
    )
    private val isScanning = MutableStateFlow(false)

    val state = combine(
        durationConstraint,
        sizeConstraint,
        isScanning
    ) { durationConstraint, sizeConstraint, isScanning ->
        ScanOptionsUiState(
            selectedDurationConstraint = durationConstraint,
            selectedSizeConstraint = sizeConstraint,
            isScanning = isScanning
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ScanOptionsUiState()
        )

    private val eventChannel = Channel<ScanOptionsEvent>()
    val events = eventChannel.receiveAsFlow()

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
        isScanning.value = true

        try {
            when (
                val soundsCount = songRepository.scanSongs(
                    minDurationSeconds = durationConstraint.value.durationSeconds,
                    minSize = sizeConstraint.value.size,
                )
            ) {
                is Result.Error -> {
                    eventChannel.send(
                        ScanOptionsEvent.ShowMessage(
                            message = soundsCount.error.toUiText()
                        )
                    )
                }

                is Result.Success -> {
                    eventChannel.send(ScanOptionsEvent.OnScanResult(soundsCount.data))
                    snackbarController.showSnackbar(
                        message = UiText.StringResource(
                            resId = R.string.scan_complete,
                            args = listOf(soundsCount.data)
                        )
                    )
                }
            }
        } finally {
            isScanning.value = false
        }
    }

    private fun onSizeConstraintSelected(sizeConstraint: SizeConstraint) {
        this.sizeConstraint.value = sizeConstraint
    }

    private fun onDurationConstraintSelected(durationConstraint: DurationConstraint) {
        this.durationConstraint.value = durationConstraint
    }
}