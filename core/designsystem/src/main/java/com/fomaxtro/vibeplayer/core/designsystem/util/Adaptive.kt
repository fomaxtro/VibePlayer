package com.fomaxtro.vibeplayer.core.designsystem.util

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

val isWideScreen: Boolean
    @Composable get() {
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

        return windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    }