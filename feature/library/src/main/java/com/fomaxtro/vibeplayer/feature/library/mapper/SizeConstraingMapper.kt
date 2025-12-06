package com.fomaxtro.vibeplayer.feature.library.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fomaxtro.vibeplayer.feature.library.R
import com.fomaxtro.vibeplayer.feature.library.model.SizeConstraint

@Composable
fun SizeConstraint.getLabel(): String {
    return stringResource(R.string.kb, size / 1024)
}