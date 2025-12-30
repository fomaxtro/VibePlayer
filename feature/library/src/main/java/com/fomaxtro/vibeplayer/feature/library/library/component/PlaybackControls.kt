package com.fomaxtro.vibeplayer.feature.library.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Composable
fun PlaybackControls(
    onShuffleClick: () -> Unit,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        VibeOutlinedButton(
            onClick = onShuffleClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = VibeIcons.Outlined.RepeatAll,
                contentDescription = stringResource(R.string.shuffle)
            )

            Text(
                text = stringResource(R.string.shuffle)
            )
        }

        VibeOutlinedButton(
            onClick = onPlayClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = VibeIcons.Outlined.Play,
                contentDescription = stringResource(DesignR.string.play)
            )

            Text(
                text = stringResource(DesignR.string.play)
            )
        }
    }
}

@Preview
@Composable
private fun PlaybackControlsPreview() {
    VibePlayerTheme {
        PlaybackControls(
            modifier = Modifier.fillMaxWidth(),
            onPlayClick = {},
            onShuffleClick = {}
        )
    }
}