package com.fomaxtro.vibeplayer.feature.scanner.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.scanner.PermissionScreen
import kotlinx.serialization.Serializable

@Serializable
data object Scanner : NavKey

fun EntryProviderScope<NavKey>.scanner() {
    entry<Scanner> {
        PermissionScreen(
            onPermissionGranted = {}
        )
    }
}