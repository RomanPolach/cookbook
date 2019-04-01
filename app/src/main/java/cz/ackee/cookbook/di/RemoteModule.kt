package cz.ackee.cookbook.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import cz.ackee.cookbook.Constants.DEVICE_ID_NAME
import cz.ackee.cookbook.model.api.AckeeInterceptor
import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.converters.InstantAdapter
import cz.ackee.cookbook.model.api.converters.Rfc3339ZonedDateTimeAdapter
import cz.ackee.cookbook.model.interactor.ApiInteractor
import cz.ackee.cookbook.model.interactor.ApiInteractorImpl
import cz.ackee.cookbook.utils.isDebug
import cz.config.ApiConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module for Rest Service.
 */
val remoteModule = module {

    single<ApiInteractor> { ApiInteractorImpl(apiDescription = get()) }

    single { AckeeInterceptor(androidContext(), get(DEVICE_ID_NAME)) }

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addNetworkInterceptor(get<AckeeInterceptor>())
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(if (isDebug) BODY else NONE))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(Rfc3339ZonedDateTimeAdapter)
                    .add(InstantAdapter)
                    .build()
            ))
            .client(get())
            .build()
            .create(ApiDescription::class.java)
    }
}