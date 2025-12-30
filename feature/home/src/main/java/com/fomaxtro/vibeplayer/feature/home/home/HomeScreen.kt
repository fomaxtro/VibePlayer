package com.fomaxtro.vibeplayer.feature.home.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.home.home.components.HomeLayout
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import com.fomaxtro.vibeplayer.feature.player.player.MiniPlayer
import com.fomaxtro.vibeplayer.feature.player.player.PlayerScreen
import com.fomaxtro.vibeplayer.feature.player.player.PlayerViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    onScanMusic: () -> Unit,
    onSearch: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                HomeAction.OnScanMusicClick -> onScanMusic()
                HomeAction.OnSearchClick -> onSearch()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit = {}
) {
    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarController = koinInject<SnackbarController>()

    val playerViewModel = koinViewModel<PlayerViewModel>()
    val playerState by playerViewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(snackbarController.events) { event ->
        snackbarHostState.showSnackbar(
            message = event.asString(context)
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        SharedTransitionLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            AnimatedContent(
                targetState = state.isPlayerExpanded
            ) { playerVisible ->
                if (playerVisible) {
                    PlayerScreen(
                        state = playerState,
                        onAction = playerViewModel::onAction,
                        onNavigateBack = {
                            onAction(HomeAction.OnCollapsePlayer)
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent
                    )
                } else {
                    HomeLayout(
                        player = {
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
                                                onAction(HomeAction.OnExpandPlayer)
                                            }
                                        },
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            }
                        }
                    ) {
                        LibraryScreen(
                            onScanMusic = {
                                onAction(HomeAction.OnScanMusicClick)
                            },
                            onSearch = {
                                onAction(HomeAction.OnSearchClick)
                            },
                            onSongClick = {
                                onAction(HomeAction.OnSongClick(it))
                            },
                            onPlayClick = {
                                onAction(HomeAction.OnPlayPlaylistClick)
                            },
                            onShuffleClick = {
                                onAction(HomeAction.OnShufflePlaylistClick)
                            },
                            songs = state.songs
                        )
                    }
                }
            }
        }
    }
}