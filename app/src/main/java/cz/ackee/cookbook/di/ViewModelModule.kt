package cz.ackee.cookbook.di

import cz.ackee.cookbook.screens.addRecipe.AddRecipeViewModel
import cz.ackee.cookbook.screens.main.MainViewModel
import cz.ackee.cookbook.screens.recipeDetail.RecipeDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Module for providing unscoped viewmodels.
 */
val viewModelModule = module {

    viewModel { MainViewModel(repository = get()) }

    viewModel { AddRecipeViewModel(repository = get()) }

    viewModel { RecipeDetailViewModel(repository = get()) }
}