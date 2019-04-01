package cz.ackee.cookbook.screens.base.activity

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.view.MenuItem
import cz.config.HockeyConfig
import net.hockeyapp.android.CrashManager
import net.hockeyapp.android.CrashManagerListener

/**
 * Extension to activity that contains common logic for all activities
 */
class CommonActivityLogicExtension(private val activity: Activity) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun checkForCrashes() {
        CrashManager.register(activity, HockeyConfig.HOCKEYAPP_ID, HockeyCrashManagerListener())
    }

    fun optionsItemSelected(item: MenuItem) {
        when (item.itemId) {
            android.R.id.home -> activity.onBackPressed()
        }
    }
}

internal class HockeyCrashManagerListener : CrashManagerListener() {
    override fun shouldAutoUploadCrashes(): Boolean {
        return HockeyConfig.HOCKEYAPP_AUTOSEND
    }
}
