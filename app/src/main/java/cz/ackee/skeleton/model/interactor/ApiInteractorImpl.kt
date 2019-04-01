package cz.ackee.skeleton.model.interactor

import cz.ackee.skeleton.model.api.ApiDescription
import cz.ackee.skeleton.model.api.Recipe
import io.reactivex.Single

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override fun getSampleData(): Single<List<Recipe>> = apiDescription.getRecipes()
}
