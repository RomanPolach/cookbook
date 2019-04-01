package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.Recipe
import io.reactivex.Single

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override fun getSampleData(): Single<List<Recipe>> = apiDescription.getRecipes()
}
