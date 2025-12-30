package com.fomaxtro.vibeplayer.feature.player.player

sealed interface PlayerAction {
    data object OnPlayPauseToggle : PlayerAction
    data object OnSkipPreviousClick : PlayerAction
    data object OnSkipNextClick : PlayerAction
    data class PlaySong(val songIndex: Int) : PlayerAction
    data class OnSeekTo(val songProgressFactor: Float) : PlayerAction
    data object OnSeekStarted : PlayerAction
    data object OnSeekCancel : PlayerAction
    data object OnToggleShuffleClick : PlayerAction
    data object OnRepeatModeClick : PlayerAction
}