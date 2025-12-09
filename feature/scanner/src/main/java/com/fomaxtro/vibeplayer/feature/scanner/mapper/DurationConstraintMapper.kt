package com.fomaxtro.vibeplayer.feature.scanner.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fomaxtro.vibeplayer.feature.scanner.R
import com.fomaxtro.vibeplayer.feature.scanner.model.DurationConstraint

@Composable
fun DurationConstraint.getLabel(): String {
    return stringResource(R.string.seconds, durationSeconds)
}