package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.Recipe

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override suspend fun getSampleData(): List<Recipe> = apiDescription.getRecipes().await()
}
