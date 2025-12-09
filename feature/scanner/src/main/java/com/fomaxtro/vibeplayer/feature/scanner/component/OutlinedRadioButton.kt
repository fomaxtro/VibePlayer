package com.fomaxtro.vibeplayer.feature.scanner.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.core.designsystem.theme.buttonPrimary30
import com.fomaxtro.vibeplayer.core.designsystem.theme.surfaceOutline

@Composable
fun OutlinedRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.buttonPrimary30
                } else {
                    MaterialTheme.colorScheme.surfaceOutline
                },
                shape = shape
            )
            .clip(shape)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.size(28.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun OutlinedRadioButtonPreview() {
    VibePlayerTheme {
        OutlinedRadioButton(
            onClick = {},
            selected = false,
            text = "30s",
            modifier = Modifier.fillMaxWidth()
        )
    }
}