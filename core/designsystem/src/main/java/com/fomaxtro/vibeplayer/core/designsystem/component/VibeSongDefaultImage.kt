package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.accentGradient

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun VibeSongDefaultImage(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .background(MaterialTheme.colorScheme.accentGradient),
        contentAlignment = Alignment.Center
    ) {
        // Magic number to fit nicely
        val factor = 0.5625f

        val maxSizeDp = minOf(maxWidth, maxHeight)
        val size = with(LocalDensity.current) {
            (maxSizeDp.toPx() * factor).toDp()
        }

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.music),
            contentDescription = null,
            modifier = Modifier.size(size)
        )
    }
}

@Preview
@Composable
private fun VibeSongDefaultImagePreview() {
    VibePlayerTheme {
        VibeSongDefaultImage(
            modifier = Modifier.size(64.dp)
        )
    }
}