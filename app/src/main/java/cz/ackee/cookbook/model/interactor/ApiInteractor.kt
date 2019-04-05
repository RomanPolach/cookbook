package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.RateReceipeRequest
import cz.ackee.cookbook.model.api.Recipe
import retrofit2.http.Body

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    suspend fun getSampleData(): List<Recipe>

    suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe

    suspend fun getRecipeDetailById(recipeId: String): Recipe

    suspend fun rateReceipeById(recipeId: String, @Body body: RateReceipeRequest): Recipe
}
