package com.fomaxtro.vibeplayer.feature.home.home

import com.fomaxtro.vibeplayer.domain.model.Song

sealed interface HomeAction {
    data object OnCollapsePlayer : HomeAction
    data object OnExpandPlayer : HomeAction
    data object OnScanMusicClick : HomeAction
    data object OnSearchClick : HomeAction
    data class OnSongClick(val song: Song) : HomeAction
    data object OnPlayPlaylistClick : HomeAction
    data object OnShufflePlaylistClick : HomeAction
}