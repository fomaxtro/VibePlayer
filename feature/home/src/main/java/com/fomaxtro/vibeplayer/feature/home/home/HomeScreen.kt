package com.fomaxtro.vibeplayer.feature.home.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import com.fomaxtro.vibeplayer.feature.library.library.LibraryViewModel
import com.fomaxtro.vibeplayer.feature.player.player.MiniPlayer
import com.fomaxtro.vibeplayer.feature.player.player.PlayerScreen
import com.fomaxtro.vibeplayer.feature.player.player.PlayerViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    onScanMusic: () -> Unit,
    onSearch: () -> Unit
) {
    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarController = koinInject<SnackbarController>()

    val playerViewModel = koinViewModel<PlayerViewModel>()
    val libraryViewModel = koinViewModel<LibraryViewModel>()
    val playerState by playerViewModel.state.collectAsStateWithLifecycle()

    var nowPlaying by rememberSaveable { mutableStateOf(false) }
    val songsListState = rememberLazyListState()

    ObserveAsEvents(snackbarController.events) { event ->
        snackbarHostState.showSnackbar(
            message = event.asString(context)
        )
    }

    LaunchedEffect(playerState.isPlaying, playerState.playingSong) {
        if (playerState.isPlaying && !nowPlaying) {
            nowPlaying = true
        }
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
                        state = playerState,
                        onAction = playerViewModel::onAction,
                        onNavigateBack = { nowPlaying = false },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            propagateMinConstraints = true
                        ) {
                            LibraryScreen(
                                songsListState = songsListState,
                                onScanMusic = onScanMusic,
                                onSearch = onSearch,
                                viewModel = libraryViewModel
                            )
                        }

                        AnimatedVisibility(
                            visible = playerState.playingSong != null,
                            enter = slideInVertically(initialOffsetY = { it }) + expandVertically(),
                            exit = slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
                        ) {
                            MiniPlayer(
                                state = playerState,
                                onAction = playerViewModel::onAction,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            nowPlaying = true
                                        }
                                    },
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