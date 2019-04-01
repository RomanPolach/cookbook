package cz.ackee.cookbook.utils

import com.crashlytics.android.Crashlytics
import net.hockeyapp.android.ExceptionHandler
import timber.log.Timber

/**
 * Tree for Timber that logs info to Crashlytics
 */
class CrashlyticsTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Crashlytics.log(priority, tag, message)
        t?.let {
            Crashlytics.logException(it)
            // store this crash also to hockeyapp
            ExceptionHandler.saveException(it, null, null)
        }
    }
}