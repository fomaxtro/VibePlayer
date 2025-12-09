package com.fomaxtro.vibeplayer.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fomaxtro.vibeplayer.core.ui.ObserveAsEvents
import com.fomaxtro.vibeplayer.core.ui.notification.SnackbarController
import com.fomaxtro.vibeplayer.core.ui.util.asString
import com.fomaxtro.vibeplayer.feature.library.navigation.LibraryRoute
import com.fomaxtro.vibeplayer.feature.library.navigation.library
import com.fomaxtro.vibeplayer.feature.onboarding.navigation.OnboardingNavKey
import com.fomaxtro.vibeplayer.feature.onboarding.navigation.onboarding
import com.fomaxtro.vibeplayer.feature.player.navigation.PlayerNavKey
import com.fomaxtro.vibeplayer.feature.player.navigation.player
import org.koin.compose.koinInject

@Composable
fun NavigationRoot() {
    val snackbarController = koinInject<SnackbarController>()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val hasMediaPermission = ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED

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
        val backStack = rememberNavBackStack(
            if (hasMediaPermission) LibraryRoute.Library() else OnboardingNavKey
        )

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
                onboarding(
                    onPermissionGranted = {
                        backStack[backStack.lastIndex] = LibraryRoute.Library(shouldScan = true)
                    }
                )

                library(
                    onSongClick = { songIndex ->
                        backStack.add(
                            PlayerNavKey(
                                songIndex = songIndex
                            )
                        )
                    },
                    onScanMusic = {
                        backStack.add(LibraryRoute.ScanMusic)
                    },
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                )

                player(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            },
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}