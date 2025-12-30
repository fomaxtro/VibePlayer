package com.fomaxtro.vibeplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fomaxtro.vibeplayer.app.MainUiState
import com.fomaxtro.vibeplayer.app.MainViewModel
import com.fomaxtro.vibeplayer.app.navigation.NavigationRoot
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.state.value is MainUiState.Loading
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb())
        )
        setContent {
            VibePlayerTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()

                if (state is MainUiState.Success) {
                    NavigationRoot(
                        hasSongs = (state as MainUiState.Success).hasSongs
                    )
                }
            }
        }
    }
}