package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.Recipe

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    suspend fun getSampleData(): List<Recipe>
}
