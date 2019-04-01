package cz.ackee.cookbook

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import cz.ackee.cookbook.di.*
import cz.ackee.cookbook.utils.CrashlyticsTree
import cz.ackee.cookbook.utils.isBeta
import cz.ackee.cookbook.utils.isDebug
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * Application class
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,
            listOf(
                appModule,
                persistenceModule,
                repositoryModule,
                remoteModule,
                viewModelModule
            ))

        if (isDebug || isBeta) {
            Stetho.initializeWithDefaults(this)
        }

        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(true)
                .build()

            Fabric.with(fabric)
            Timber.plant(CrashlyticsTree())
        }

        AndroidThreeTen.init(this)
    }
}