package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.Recipe
import io.reactivex.Single

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    fun getSampleData(): Single<List<Recipe>>
}
