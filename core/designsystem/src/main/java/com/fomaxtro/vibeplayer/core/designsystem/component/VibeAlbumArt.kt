package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibeAlbumArt(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp)
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape),
        error = {
            VibeGradientIcon(
                icon = VibeIcons.Duotone.Music,
                contentDescription = null,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    )
}

@Preview
@Composable
private fun VibeAlbumArtPreview() {
    VibePlayerTheme {
        VibeAlbumArt(
            imageUrl = null,
            contentDescription = null
        )
    }
}