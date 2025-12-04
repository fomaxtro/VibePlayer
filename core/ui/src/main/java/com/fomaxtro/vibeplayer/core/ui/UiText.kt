package com.fomaxtro.vibeplayer.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class StringResource(@param:StringRes val resId: Int) : UiText
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.StringResource -> stringResource(resId)
    }
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.StringResource -> context.getString(resId)
    }
}