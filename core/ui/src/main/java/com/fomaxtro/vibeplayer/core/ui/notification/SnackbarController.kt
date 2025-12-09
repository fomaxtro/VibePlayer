package com.fomaxtro.vibeplayer.core.ui.notification

import com.fomaxtro.vibeplayer.core.ui.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SnackbarController {
    private val eventChannel = Channel<UiText>()
    val events = eventChannel.receiveAsFlow()

    suspend fun showSnackbar(message: UiText) {
        eventChannel.send(message)
    }
}