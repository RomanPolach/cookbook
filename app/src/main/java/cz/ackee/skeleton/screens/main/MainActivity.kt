package cz.ackee.skeleton.screens.main

import android.os.Bundle
import cz.ackee.skeleton.R
import cz.ackee.skeleton.screens.base.activity.FragmentActivity

/**
 * Main activity of application
 */
class MainActivity : FragmentActivity() {

    override val fragmentName: String? = MainFragment::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
}