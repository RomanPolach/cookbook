package cz.ackee.skeleton

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import cz.ackee.skeleton.di.*
import cz.ackee.skeleton.utils.CrashlyticsTree
import cz.ackee.skeleton.utils.isBeta
import cz.ackee.skeleton.utils.isDebug
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * Application class
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        checkPackageName()
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

    private fun checkPackageName() {
        if (BuildConfig.APPLICATION_ID.contains("ackee")
            || BuildConfig.APPLICATION_ID.contains("skeleton")
            || packageName.contains("ackee")
            || packageName.contains("skeleton")
            || packageManager.getApplicationInfo(BuildConfig.APPLICATION_ID, 0).nonLocalizedLabel.contains("Ackee skeleton")) {
            throw IllegalStateException(
                "Are you sure you have correctly changed the app's package name? Using 'ackee' or 'skeleton' in package name is highly suspicious!🕵")
        }
    }
}