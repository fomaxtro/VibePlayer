package com.fomaxtro.vibeplayer.feature.library.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.library.permission.PermissionScreen
import com.fomaxtro.vibeplayer.feature.library.library.LibraryScreen
import kotlinx.serialization.Serializable

@Serializable
data object Permission : NavKey

@Serializable
data class Library(val shouldScan: Boolean = false) : NavKey

fun EntryProviderScope<NavKey>.scanner(
    backStack: NavBackStack<NavKey>
) {
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