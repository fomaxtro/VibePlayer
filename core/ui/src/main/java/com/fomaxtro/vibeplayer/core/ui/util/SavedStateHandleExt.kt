package com.fomaxtro.vibeplayer.core.ui.util

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun SavedStateHandle.getTextFieldState(
    scope: CoroutineScope,
    key: String,
    initialValue: String
): TextFieldState {
    return TextFieldState(get(key) ?: initialValue)
        .also { state ->
            snapshotFlow { state.text.toString() }
                .onEach {
                    set(key, it)
                }
                .launchIn(scope)
        }
}