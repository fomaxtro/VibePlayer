package com.fomaxtro.vibeplayer.feature.library.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.library.R

@Composable
fun PlaybackControls(
    onShuffleClick: () -> Unit,
    onPlayClick: () -> Unit,
    songsCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Text(
            text = pluralStringResource(
                id = R.plurals.songs_available,
                count = songsCount,
                songsCount
            ),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun PlaybackControlsPreview() {
    VibePlayerTheme {
        PlaybackControls(
            modifier = Modifier.fillMaxWidth(),
            onPlayClick = {},
            onShuffleClick = {},
            songsCount = 5
        )
    }
}