package com.fomaxtro.vibeplayer.app

import androidx.compose.runtime.Immutable

@Immutable
interface MainUiState {
    data object Loading : MainUiState

    data class Success(
        val hasSongs: Boolean = false
    ) : MainUiState
}