package cz.ackee.cookbook.screens.main

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.SampleRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.viewmodel.RxAwareViewModel
import io.reactivex.Observable

/**
 * View model for main screen
 */
class MainViewModel(val repository: SampleRepository) : RxAwareViewModel() {

    fun observeState(): Observable<State<List<Recipe>>> = repository.observeSampleData()
}