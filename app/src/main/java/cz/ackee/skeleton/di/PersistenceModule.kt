package cz.ackee.skeleton.di

import android.content.Context
import cz.ackee.skeleton.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * Module for providing persistence related dependencies.
 */
val persistenceModule = module {

    single { androidApplication().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE) }
}