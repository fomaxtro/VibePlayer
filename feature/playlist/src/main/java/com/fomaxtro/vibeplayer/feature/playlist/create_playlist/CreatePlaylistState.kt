package com.fomaxtro.vibeplayer.feature.playlist.create_playlist

import androidx.compose.foundation.text.input.TextFieldState

data class CreatePlaylistState(
    val playlistName: TextFieldState,
    val canSubmit: Boolean = false
)
