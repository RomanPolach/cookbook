package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.ApiDescription
import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.RateReceipeRequest
import cz.ackee.cookbook.model.api.Recipe
import retrofit2.http.Body

/**
 * Implementation of [ApiInteractor]
 */
class ApiInteractorImpl(private val apiDescription: ApiDescription) : ApiInteractor {

    override suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe = apiDescription.sendRecipe(recipe).await()

    override suspend fun getRecipeDetailById(recipeId: String): Recipe = apiDescription.getRecipeDetailById(recipeId).await()

    override suspend fun rateReceipeById(recipeId: String, @Body body: RateReceipeRequest): Recipe =
        apiDescription.rateReceipeById(recipeId, body).await()

    override suspend fun getRecipeList(): List<Recipe> = apiDescription.getRecipes().await()
}
