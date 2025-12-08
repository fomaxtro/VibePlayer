package com.fomaxtro.vibeplayer.core.data.util

import android.database.sqlite.SQLiteFullException
import com.fomaxtro.vibeplayer.core.common.Result
import com.fomaxtro.vibeplayer.domain.error.DataError
import kotlinx.coroutines.CancellationException
import timber.log.Timber

inline fun <D> safeDatabaseCall(
    block: () -> D
): Result<D, DataError> {
    return try {
        Result.Success(block())
    } catch (e: SQLiteFullException) {
        Timber.tag("safeDatabaseCall").e(e)

        Result.Error(DataError.Local.DISK_FULL)
    } catch (e: Exception) {
        if (e is CancellationException) throw e

        Timber.tag("safeDatabaseCall").e(e)

        Result.Error(DataError.Local.DISK_FULL)
    }
}