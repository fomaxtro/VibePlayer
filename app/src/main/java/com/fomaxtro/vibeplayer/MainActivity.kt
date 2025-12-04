package com.fomaxtro.vibeplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.scanner.navigation.Scanner
import com.fomaxtro.vibeplayer.feature.scanner.navigation.scanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb())
        )
        setContent {
            VibePlayerTheme {
                val backStack = rememberNavBackStack(Scanner)

                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        backStack.removeLast()
                    },
                    entryProvider = entryProvider {
                        scanner()
                    }
                )
            }
        }
    }
}