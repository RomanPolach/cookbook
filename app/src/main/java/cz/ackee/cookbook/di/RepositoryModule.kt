package cz.ackee.cookbook.di

import cz.ackee.cookbook.model.repository.SampleRepository
import cz.ackee.cookbook.model.repository.SampleRepositoryImpl
import org.koin.dsl.module.module

/**
 * Module for providing repositories.
 */
val repositoryModule = module {

    single<SampleRepository> { SampleRepositoryImpl(apiInteractor = get()) }
}