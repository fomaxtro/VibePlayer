package com.fomaxtro.vibeplayer.feature.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme

@Composable
fun RadioGroup(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable FlowRowScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Preview
@Composable
private fun RadioGroupPreview() {
    VibePlayerTheme {
        RadioGroup(
            title = "Ignore duration less than"
        ) {
            OutlinedRadioButton(
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f),
                text = "30s"
            )

            OutlinedRadioButton(
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
                text = "30s"
            )
        }
    }
}