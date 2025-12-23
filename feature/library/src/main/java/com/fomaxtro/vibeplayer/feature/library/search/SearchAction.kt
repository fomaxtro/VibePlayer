package com.fomaxtro.vibeplayer.feature.library.search

sealed interface SearchAction {
    data object OnClearClick : SearchAction
    data object OnCancelClick : SearchAction
}