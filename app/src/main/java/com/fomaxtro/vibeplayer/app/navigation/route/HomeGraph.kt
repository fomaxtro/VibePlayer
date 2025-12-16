package com.fomaxtro.vibeplayer.app.navigation.route

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

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
        LibraryScreen(
            onSongClick = onSongClick,
            onScanMusic = onScanMusic,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}