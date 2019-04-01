package cz.ackee.cookbook.di

import android.content.Context
import cz.ackee.cookbook.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * Module for providing persistence related dependencies.
 */
val persistenceModule = module {

    single { androidApplication().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE) }
}