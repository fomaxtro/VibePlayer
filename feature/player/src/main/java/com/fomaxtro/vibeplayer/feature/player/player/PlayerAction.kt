package com.fomaxtro.vibeplayer.feature.player.player

sealed interface PlayerAction {
    data object OnNavigateBackClick : PlayerAction
}