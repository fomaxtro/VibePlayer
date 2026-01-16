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
        val Plus: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.plus)
        val Check: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.check)
    }

    object Outlined {
        val Play: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.play_outlined)
        val RepeatAll: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.repeat)
        val RepeatOne: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.repeat_one)
        val RepeatOff: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.repeat_off)
        val Scan: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.scan)
        val Search: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.search)
        val Shuffle: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.shuffle)
        val Menu: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.menu_dots)
    }

    object Duotone {
        val Music: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.music_duotone)
        val Favourite: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.favourite_duotone)
        val Playlist: ImageVector
            @Composable get() = ImageVector.vectorResource(R.drawable.playlist_duotone)
    }
}