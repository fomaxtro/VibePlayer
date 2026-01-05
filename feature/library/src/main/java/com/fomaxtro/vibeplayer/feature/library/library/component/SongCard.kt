package com.fomaxtro.vibeplayer.feature.library.library.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeAlbumArt
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMediaCard
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun SongCard(
    onClick: () -> Unit,
    imageUrl: String?,
    title: String,
    artist: String,
    duration: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
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
        action = {
            Text(duration)
        },
        modifier = modifier,
        contentPadding = contentPadding
    )
}

@Preview
@Composable
private fun SongCardPreview() {
    VibePlayerTheme {
        SongCard(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = null,
            title = "505",
            artist = "Arctic Monkeys",
            duration = "4:20",
            onClick = {}
        )
    }
}