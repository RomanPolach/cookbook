package cz.ackee.skeleton.di

import cz.ackee.skeleton.model.repository.SampleRepository
import cz.ackee.skeleton.model.repository.SampleRepositoryImpl
import org.koin.dsl.module.module

/**
 * Module for providing repositories.
 */
val repositoryModule = module {

    single<SampleRepository> { SampleRepositoryImpl(apiInteractor = get()) }
}