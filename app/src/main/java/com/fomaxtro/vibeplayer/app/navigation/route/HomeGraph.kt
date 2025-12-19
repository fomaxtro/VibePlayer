package com.fomaxtro.vibeplayer.app.navigation.route

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import com.fomaxtro.vibeplayer.feature.player.player.MiniPlayer
import com.fomaxtro.vibeplayer.feature.player.player.PlayerAction
import com.fomaxtro.vibeplayer.feature.player.player.PlayerScreen
import com.fomaxtro.vibeplayer.feature.player.player.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Serializable
data object HomeNavKey : NavKey

fun EntryProviderScope<NavKey>.home(
    onScanMusic: () -> Unit
) {
    entry<HomeNavKey> {
        HomeScreen(
            onScanMusic = onScanMusic
        )
    }
}

@Composable
private fun HomeScreen(
    onScanMusic: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarController = koinInject<SnackbarController>()

    val viewModel = koinViewModel<PlayerViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var nowPlaying by rememberSaveable { mutableStateOf(false) }
    val songsListState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    ObserveAsEvents(snackbarController.events) { event ->
        snackbarHostState.showSnackbar(
            message = event.asString(context)
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = Modifier.imePadding()
    ) { innerPadding ->
        SharedTransitionLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            AnimatedContent(
                targetState = nowPlaying
            ) { playerVisible ->
                if (playerVisible) {
                    PlayerScreen(
                        state = state,
                        onAction = viewModel::onAction,
                        onNavigateBack = { nowPlaying = false },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LibraryScreen(
                            onSongClick = { songIndex ->
                                val delay = if (isImeVisible) 500L else 150L

                                keyboardController?.hide()
                                viewModel.onAction(PlayerAction.PlaySong(songIndex))

                                scope.launch {
                                    // Make sure Keyboard is Hide and MiniPlayer displayed
                                    delay(delay)
                                    nowPlaying = true
                                }
                            },
                            onScanMusic = onScanMusic,
                            modifier = Modifier.weight(1f),
                            songsListState = songsListState
                        )

                        AnimatedVisibility(
                            visible = state.playingSong != null,
                            enter = slideInVertically(initialOffsetY = { it }) + expandVertically(),
                            exit = slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
                        ) {
                            MiniPlayer(
                                state = state,
                                onAction = viewModel::onAction,
                                modifier = Modifier.fillMaxWidth(),
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        }
                    }
                }
            }
        }
    }
}