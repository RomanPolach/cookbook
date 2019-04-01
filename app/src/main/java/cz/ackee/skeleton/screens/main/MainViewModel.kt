package cz.ackee.skeleton.screens.main

import cz.ackee.skeleton.model.api.Recipe
import cz.ackee.skeleton.model.repository.SampleRepository
import cz.ackee.skeleton.model.repository.State
import cz.ackee.skeleton.screens.base.viewmodel.RxAwareViewModel
import io.reactivex.Observable

/**
 * View model for main screen
 */
class MainViewModel(val repository: SampleRepository) : RxAwareViewModel() {

    fun observeState(): Observable<State<List<Recipe>>> = repository.observeSampleData()
}