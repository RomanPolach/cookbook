package cz.ackee.cookbook.di

import android.content.Context
import androidx.room.Room
import cz.ackee.cookbook.Constants
import cz.ackee.cookbook.model.api.db.RoomStore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

/**
 * Module for providing persistence related dependencies.
 */
val persistenceModule = module {

    single { androidApplication().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE) }

    single {
        Room.databaseBuilder(androidContext(), RoomStore::class.java, "mydb")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<RoomStore>().recipeDao()
    }
}