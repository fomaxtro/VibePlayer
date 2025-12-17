package com.fomaxtro.vibeplayer.core.designsystem.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.fomaxtro.vibeplayer.core.designsystem.R

object VibeIcons {
    object Filled {
        val ArrowUp: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.arrow)
        val ArrowLeft: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.arrow_left)
        val ChevronDown: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.chevron_down)
        val Pause: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.pause)
        val Play: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.play)
        val SkipNext: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.skip_next)
        val SkipPrevious: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.skip_previous)
        val Logo: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.logo)
        val Close: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.close)
    }

    object Outlined {
        val Play: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.play_outlined)
        val Repeat: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.repeat)
        val Scan: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.scan)
        val Search: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.search)
    }
}