package com.fomaxtro.vibeplayer.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = ButtonPrimary,
    onPrimary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = OnSurface,
    onSurfaceVariant = TextSecondary,
    outline = Outline,
    secondary = Accent,
    background = SurfaceBg,
    surface = SurfaceBg
)

val ColorScheme.buttonPrimary30: Color
    get() = ButtonPrimary30

val ColorScheme.buttonHover: Color
    get() = ButtonHover

val ColorScheme.accentGradient: Brush
    get() = AccentGradient

val ColorScheme.surfaceOutline: Color
    get() = SurfaceOutline

val ColorScheme.surfaceHigher: Color
    get() = SurfaceHigher

val ColorScheme.textDisabled: Color
    get() = TextDisabled

val ColorScheme.surfaceBg30: Color
    get() = SurfaceBg.copy(alpha = 0.3f)

@Composable
fun VibePlayerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}