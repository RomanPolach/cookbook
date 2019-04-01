package cz.ackee.cookbook.di

import cz.ackee.cookbook.Constants.DEVICE_ID_NAME
import cz.ackee.cookbook.utils.DeviceUuidFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * Main application module.
 */
val appModule = module {

    single { DeviceUuidFactory(androidApplication(), prefs = get()) }

    single(name = DEVICE_ID_NAME) { get<DeviceUuidFactory>().deviceUuid.toString() }
}