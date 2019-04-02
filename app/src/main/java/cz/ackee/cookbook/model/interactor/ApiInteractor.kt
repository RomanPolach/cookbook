package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.Recipe
import kotlinx.coroutines.Deferred

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    fun getSampleData(): Deferred<List<Recipe>>
}
