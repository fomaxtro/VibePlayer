package com.fomaxtro.vibeplayer.feature.player.player

sealed interface PlayerAction {
    data object OnPlayPauseToggle : PlayerAction
    data object OnSkipPreviousClick : PlayerAction
    data object OnSkipNextClick : PlayerAction
    data class PlaySong(val songIndex: Int) : PlayerAction
}