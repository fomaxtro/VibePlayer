package com.fomaxtro.vibeplayer.core.designsystem.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.DropShadow

fun Modifier.vibeShadow(
    shape: Shape
): Modifier {
    return dropShadow(
        shape = shape,
        shadow = Shadow(
            color = DropShadow,
            radius = 8.dp,
            offset = DpOffset(0.dp, 2.dp)
        )
    )
}