package com.fomaxtro.vibeplayer.core.ui.util

sealed interface Resource<out D> {
    data object Loading : Resource<Nothing>
    data class Success<D>(val data: D) : Resource<D>
    data class Error(val error: UiText) : Resource<Nothing>
}

fun <D> Resource<D>.getOrThrow(): D {
    return when (this) {
        is Resource.Success -> data
        is Resource.Error -> throw IllegalStateException("Expected Success but found Error: $error")
        Resource.Loading -> throw IllegalStateException("Expected Success but found Loading state")
    }
}