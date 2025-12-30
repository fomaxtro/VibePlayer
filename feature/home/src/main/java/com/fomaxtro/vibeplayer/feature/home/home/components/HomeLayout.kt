package com.fomaxtro.vibeplayer.feature.home.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.util.isWideScreen

@Composable
fun HomeLayout(
    player: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (!isWideScreen) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f),
                propagateMinConstraints = true
            ) {
                content()
            }

            player()
        }
    } else {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()

            Box(
                modifier = Modifier
                    .width(480.dp)
                    .align(Alignment.BottomCenter),
                propagateMinConstraints = true
            ) {
                player()
            }
        }
    }
}