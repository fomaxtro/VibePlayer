package com.fomaxtro.vibeplayer.navigation.route

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeMainTopAppBar
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.library.navigation.LibraryNavKey
import com.fomaxtro.vibeplayer.feature.library.navigation.library
import com.fomaxtro.vibeplayer.feature.scanner.navigation.ScanProgressNavKey
import com.fomaxtro.vibeplayer.feature.scanner.navigation.scanProgress
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import com.fomaxtro.vibeplayer.core.designsystem.R as DesignR

@Serializable
data object HomeNavKey : NavKey

fun EntryProviderScope<NavKey>.home(
    onScanMusic: () -> Unit,
    onSongClick: (songIndex: Int) -> Unit
) {
    entry<HomeNavKey> {
        HomeScreen(
            onScanMusic = onScanMusic,
            onSongClick = onSongClick
        )
    }
}

@Composable
private fun HomeScreen(
    onScanMusic: () -> Unit,
    onSongClick: (songIndex: Int) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarController = koinInject<SnackbarController>()
    val context = LocalContext.current

    val backStack = rememberNavBackStack(LibraryNavKey)

    ObserveAsEvents(snackbarController.events) { event ->
        snackbarHostState.showSnackbar(
            message = event.asString(context)
        )
    }

    Scaffold(
        topBar = {
            VibeMainTopAppBar(
                actions = {
                    VibeIconButton(
                        onClick = onScanMusic
                    ) {
                        Icon(
                            imageVector = VibeIcons.Scan,
                            contentDescription = stringResource(DesignR.string.scan)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                library(
                    onSongClick = onSongClick,
                    onScanAgain = {
                        backStack[backStack.lastIndex] = ScanProgressNavKey
                    }
                )

                scanProgress(
                    onScanFinish = {
                        backStack[backStack.lastIndex] = LibraryNavKey
                    }
                )
            },
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}