package com.fomaxtro.vibeplayer.feature.playlist.playlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedTextField
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.playlist.R

@Composable
fun PlaylistCreateSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.create_new_playlist),
            style = MaterialTheme.typography.titleMedium
        )

        VibeOutlinedTextField(
            state = TextFieldState(),
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.enter_playlist_name)
        )
    }
}

@Preview
@Composable
private fun PlaylistCreateSheetContentPreview() {
    VibePlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        ) {
            PlaylistCreateSheetContent()
        }
    }
}