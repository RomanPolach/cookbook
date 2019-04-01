package cz.ackee.skeleton.model.interactor

import cz.ackee.skeleton.model.api.Recipe
import io.reactivex.Single

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    fun getSampleData(): Single<List<Recipe>>
}
