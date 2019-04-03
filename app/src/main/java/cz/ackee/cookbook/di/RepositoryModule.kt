package cz.ackee.cookbook.di

import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.RecipeRepositoryImpl

import org.koin.dsl.module.module

/**
 * Module for providing repositories.
 */
val repositoryModule = module {

    single<RecipeRepository> { RecipeRepositoryImpl(apiInteractor = get()) }
}