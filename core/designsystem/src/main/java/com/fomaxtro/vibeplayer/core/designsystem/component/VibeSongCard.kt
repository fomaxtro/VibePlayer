package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeSongCard(
    onClick: () -> Unit,
    imageUrl: String?,
    title: String,
    artist: String,
    duration: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    leadingContent: @Composable (() -> Unit)? = null
) {
    VibeMediaCard(
        onClick = onClick,
        image = {
            VibeAlbumArt(
                imageUrl = imageUrl,
                contentDescription = null
            )
        },
        title = title,
        subtitle = artist,
        action = { Text(duration) },
        modifier = modifier,
        contentPadding = contentPadding,
        leadingContent = leadingContent
    )
}

@Preview
@Composable
private fun VibeSongCardPreview() {
    VibePlayerTheme {
        VibeSongCard(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = null,
            title = "505",
            artist = "Arctic Monkeys",
            duration = "4:20",
            onClick = {}
        )
    }
}