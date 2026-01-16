package com.fomaxtro.vibeplayer.domain.error

import com.fomaxtro.vibeplayer.core.common.Error

sealed interface DataError : Error {
    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }

    enum class Resource : DataError {
        ALREADY_EXISTS
    }
}