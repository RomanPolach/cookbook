package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.Recipe
import kotlinx.coroutines.Deferred

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override fun getSampleData(): Deferred<List<Recipe>> = apiDescription.getRecipes()
}
