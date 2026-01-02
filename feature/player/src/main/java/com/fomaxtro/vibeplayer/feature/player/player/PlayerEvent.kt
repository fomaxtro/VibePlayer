package com.fomaxtro.vibeplayer.feature.player.player

import com.fomaxtro.vibeplayer.core.ui.util.UiText

sealed interface PlayerEvent {
    data class ShowSystemMessage(val message: UiText) : PlayerEvent
}