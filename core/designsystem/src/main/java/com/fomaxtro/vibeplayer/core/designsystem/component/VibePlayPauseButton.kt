package com.fomaxtro.vibeplayer.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.R
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun VibePlayPauseButton(
    onClick: () -> Unit,
    playing: Boolean,
    modifier: Modifier = Modifier,
    colors: VibeIconButtonColors = VibeIconButtonDefaults.colors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.background
    ),
    enabled: Boolean = true,
) {
    var buttonSize by remember { mutableIntStateOf(0) }
    val buttonSizeDp = with(LocalDensity.current) {
        (buttonSize * 0.4f).toDp()
    }

    VibeIconButton(
        onClick = onClick,
        modifier = modifier
            .onSizeChanged {
                buttonSize = minOf(it.height, it.width)
            },
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            imageVector = if (playing) {
                VibeIcons.Filled.Pause
            } else {
                VibeIcons.Filled.Play
            },
            contentDescription = if (playing) {
                stringResource(R.string.pause)
            } else {
                stringResource(R.string.play)
            },
            modifier = Modifier.size(buttonSizeDp)
        )
    }
}

@Preview
@Composable
private fun VibePlayPauseButtonPreview() {
    VibePlayerTheme {
        VibePlayPauseButton(
            onClick = {},
            playing = true
        )
    }
}