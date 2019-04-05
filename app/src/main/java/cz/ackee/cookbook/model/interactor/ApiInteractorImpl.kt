package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.Recipe

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe = apiDescription.sendRecipe(recipe).await()

    override suspend fun getRecipeList(): List<Recipe> = apiDescription.getRecipes().await()
}
