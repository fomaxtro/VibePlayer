package com.fomaxtro.vibeplayer.feature.library.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fomaxtro.vibeplayer.feature.library.permission.PermissionScreen
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import com.fomaxtro.vibeplayer.feature.library.scan_music.ScanMusicScreen
import kotlinx.serialization.Serializable

@Serializable
data object LibraryNavKey : NavKey

private sealed interface LibraryRoute : NavKey {
    @Serializable
    data object Permission : LibraryRoute

    @Serializable
    data class Library(val shouldScan: Boolean = false) : LibraryRoute

    @Serializable
    data object ScanMusic : LibraryRoute
}

fun EntryProviderScope<NavKey>.library(
    onSongClick: (songId: Long) -> Unit
) {
    entry<LibraryNavKey> {
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
            if (hasMediaPermission) LibraryRoute.Library() else LibraryRoute.Permission
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
                entry<LibraryRoute.Permission> {
                    PermissionScreen(
                        onPermissionGranted = {
                            if (backStack.lastOrNull() is LibraryRoute.Permission) {
                                backStack[backStack.lastIndex] = LibraryRoute.Library(shouldScan = true)
                            }
                        }
                    )
                }

                entry<LibraryRoute.Library> {
                    LibraryScreen(
                        autoScan = it.shouldScan,
                        onScanMusic = {
                            if (backStack.lastOrNull() is LibraryRoute.Library) {
                                backStack.add(LibraryRoute.ScanMusic)
                            }
                        },
                        onSongClick = onSongClick
                    )
                }

                entry<LibraryRoute.ScanMusic> {
                    ScanMusicScreen(
                        onNavigateBackClick = {
                            if (backStack.lastOrNull() is LibraryRoute.ScanMusic) {
                                backStack.removeLastOrNull()
                            }
                        }
                    )
                }
            }
        )
    }
}