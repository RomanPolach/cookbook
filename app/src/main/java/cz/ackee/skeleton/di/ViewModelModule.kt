package cz.ackee.skeleton.di

import cz.ackee.skeleton.screens.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Module for providing unscoped viewmodels.
 */
val viewModelModule = module {

    viewModel { MainViewModel(repository = get()) }
}