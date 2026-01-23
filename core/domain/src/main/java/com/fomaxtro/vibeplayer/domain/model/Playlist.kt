package com.fomaxtro.vibeplayer.domain.model

data class Playlist(
    val id: Long,
    val name: String,
    val songsCount: Int,
    val albumArtUri: String?
) {
    companion object {
        const val MAX_NAME_LENGTH = 40

        fun isValidName(name: String): Boolean =
            name.isNotBlank() && name.length <= MAX_NAME_LENGTH
    }
}
