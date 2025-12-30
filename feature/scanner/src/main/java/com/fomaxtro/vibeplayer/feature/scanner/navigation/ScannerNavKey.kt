package com.fomaxtro.vibeplayer.feature.scanner.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.fomaxtro.vibeplayer.feature.scanner.scan_options.ScanOptionsScreen
import com.fomaxtro.vibeplayer.feature.scanner.scan_progress.ScanProgressScreen
import kotlinx.serialization.Serializable

@Serializable
data object ScanProgressNavKey : NavKey

@Serializable
data object ScanOptionsNavKey : NavKey

fun EntryProviderScope<NavKey>.scanner(
    onScanFinish: () -> Unit,
    onScanOptions: () -> Unit,
    onNavigateBack: () -> Unit,
    onScanFilteredResult: (songsCount: Int) -> Unit
) {
    entry<ScanProgressNavKey> {
        ScanProgressScreen(
            onScanFinish = onScanFinish,
            onScanOptions = onScanOptions
        )
    }

    entry<ScanOptionsNavKey> {
        ScanOptionsScreen(
            onNavigateBack = onNavigateBack,
            onScanResult = onScanFilteredResult
        )
    }
}