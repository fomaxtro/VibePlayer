package com.fomaxtro.vibeplayer.feature.playlist.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeGradientIcon
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMediaCard
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.playlist.R

@Composable
fun PlaylistScreen() {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 4.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "1 Playlist",
                        style = MaterialTheme.typography.labelLarge
                    )

                    VibeIconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = VibeIcons.Filled.Plus,
                            contentDescription = stringResource(R.string.create_playlist)
                        )
                    }
                }
            }

            item {
                VibeMediaCard(
                    onClick = {},
                    image = {
                        VibeGradientIcon(
                            icon = VibeIcons.Duotone.Favourite,
                            contentDescription = null,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    },
                    title = "Favourite",
                    subtitle = "2 Songs",
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    action = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = VibeIcons.Outlined.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlaylistScreenPreview() {
    VibePlayerTheme {
        PlaylistScreen()
    }
}
