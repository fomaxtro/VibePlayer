package com.fomaxtro.vibeplayer.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fomaxtro.vibeplayer.feature.onboarding.navigation.OnboardingNavKey
import com.fomaxtro.vibeplayer.feature.onboarding.navigation.onboarding
import com.fomaxtro.vibeplayer.feature.player.navigation.PlayerNavKey
import com.fomaxtro.vibeplayer.feature.player.navigation.player
import com.fomaxtro.vibeplayer.feature.scanner.navigation.ScanOptionsNavKey
import com.fomaxtro.vibeplayer.feature.scanner.navigation.scanOptions
import com.fomaxtro.vibeplayer.navigation.route.HomeNavKey
import com.fomaxtro.vibeplayer.navigation.route.home

@Composable
fun NavigationRoot() {
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

    val backStack = rememberNavBackStack(
        if (hasMediaPermission) HomeNavKey else OnboardingNavKey
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
                    backStack[backStack.lastIndex] = HomeNavKey
                }
            )

            home(
                hasMediaPermission = hasMediaPermission,
                onScanMusic = {
                    backStack.add(ScanOptionsNavKey)
                },
                onSongClick = { songIndex ->
                    backStack.add(
                        PlayerNavKey(
                            songIndex = songIndex
                        )
                    )
                }
            )

            scanOptions(
                onNavigateBack = {
                    backStack.removeLastOrNull()
                }
            )

            player(
                onNavigateBack = {
                    backStack.removeLastOrNull()
                }
            )
        }
    )
}