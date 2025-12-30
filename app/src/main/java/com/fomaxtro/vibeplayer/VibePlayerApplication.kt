package com.fomaxtro.vibeplayer

import android.app.Application
import com.fomaxtro.vibeplayer.app.di.appModule
import com.fomaxtro.vibeplayer.core.data.di.coreDataModule
import com.fomaxtro.vibeplayer.core.player.di.corePlayerModule
import com.fomaxtro.vibeplayer.core.ui.di.coreUiModule
import com.fomaxtro.vibeplayer.domain.di.coreDomainModule
import com.fomaxtro.vibeplayer.feature.home.di.featureHomeModule
import com.fomaxtro.vibeplayer.feature.library.di.featureLibraryModule
import com.fomaxtro.vibeplayer.feature.player.di.featurePlayerModule
import com.fomaxtro.vibeplayer.feature.scanner.di.featureScannerModule
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class VibePlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@VibePlayerApplication)
            androidLogger()
            analytics()

            modules(
                appModule,
                coreDataModule,
                coreDomainModule,
                coreUiModule,
                corePlayerModule,
                featureLibraryModule,
                featurePlayerModule,
                featureScannerModule,
                featureHomeModule
            )
        }
    }
}