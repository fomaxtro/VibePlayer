package com.fomaxtro.vibeplayer.core.common

sealed interface Result<out D, out E> {
    data class Success<D>(val data: D) : Result<D, Nothing>
    data class Error<E : com.fomaxtro.vibeplayer.core.common.Error>(val error: E) : Result<Nothing, E>
}