package com.fomaxtro.vibeplayer.feature.onboarding.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.onboarding.permission.PermissionScreen
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingNavKey : NavKey

fun EntryProviderScope<NavKey>.onboarding(
    onPermissionGranted: () -> Unit
) {
    entry<OnboardingNavKey> {
        PermissionScreen(
            onPermissionGranted = onPermissionGranted
        )
    }
}