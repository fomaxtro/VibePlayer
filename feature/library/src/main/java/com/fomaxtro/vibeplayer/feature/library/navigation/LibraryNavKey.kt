package com.fomaxtro.vibeplayer.feature.library.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.permission.PermissionScreen
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import kotlinx.serialization.Serializable

@Serializable
data object LibraryNavKey : NavKey

@Serializable
internal data object Permission : NavKey

@Serializable
internal data class Library(val shouldScan: Boolean = false) : NavKey

fun EntryProviderScope<NavKey>.scanner(
    backStack: NavBackStack<NavKey>
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

        LaunchedEffect(Unit) {
            if (hasMediaPermission) {
                backStack[backStack.lastIndex] = Library()
            } else {
                backStack[backStack.lastIndex] = Permission
            }
        }
    }

    entry<Permission> {
        PermissionScreen(
            onPermissionGranted = {
                if (backStack.last() is Permission) {
                    backStack[backStack.lastIndex] = Library(shouldScan = true)
                }
            }
        )
    }

    entry<Library> {
        LibraryScreen(
            autoScan = it.shouldScan
        )
    }
}