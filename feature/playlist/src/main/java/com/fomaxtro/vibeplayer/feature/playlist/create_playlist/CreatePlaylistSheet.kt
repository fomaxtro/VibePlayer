package com.fomaxtro.vibeplayer.feature.playlist.create_playlist

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedTextField
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.domain.model.Playlist
import com.fomaxtro.vibeplayer.feature.playlist.R
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaylistSheet(
    onPlaylistCreated: (playlistId: Long) -> Unit,
    onDismiss: () -> Unit,
    viewModel: CreatePlaylistViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CreatePlaylistEvent.ShowMessage -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asString(context)
                )
            }
            is CreatePlaylistEvent.PlaylistCreated -> {
                sheetState.hide()
                onPlaylistCreated(event.playlistId)
            }
            CreatePlaylistEvent.Cancel -> {
                sheetState.hide()
                onDismiss()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            CreatePlaylistSheetContent(
                state = state,
                onAction = viewModel::onAction
            )

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CreatePlaylistSheetContent(
    state: CreatePlaylistState,
    onAction: (CreatePlaylistAction) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.create_new_playlist),
            style = MaterialTheme.typography.titleMedium
        )

        VibeOutlinedTextField(
            state = state.playlistName,
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.enter_playlist_name),
            suffix = {
                Text(
                    text = "${state.playlistName.text.length}/${Playlist.MAX_NAME_LENGTH}"
                )
            },
            inputTransformation = InputTransformation.maxLength(Playlist.MAX_NAME_LENGTH),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            onKeyboardAction = {
                focusManager.clearFocus()
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VibeOutlinedButton(
                onClick = {
                    onAction(CreatePlaylistAction.OnCancelClick)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.cancel))
            }

            VibeButton(
                onClick = {
                    onAction(CreatePlaylistAction.OnCreateClick)
                },
                modifier = Modifier.weight(1f),
                enabled = state.canSubmit
            ) {
                Text(text = stringResource(R.string.create))
            }
        }
    }
}

private class CreatePlaylistParameterProvider : PreviewParameterProvider<CreatePlaylistState> {
    override val values: Sequence<CreatePlaylistState> = sequenceOf(
        CreatePlaylistState(
            playlistName = TextFieldState(
                initialText = "a".repeat(20)
            ),
            canSubmit = true
        ),
        CreatePlaylistState(
            playlistName = TextFieldState(),
            canSubmit = false
        )
    )
}

@Preview
@Composable
private fun CreatePlaylistSheetContentPreview(
    @PreviewParameter(CreatePlaylistParameterProvider::class) state: CreatePlaylistState
) {
    VibePlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        ) {
            CreatePlaylistSheetContent(
                state = state
            )
        }
    }
}