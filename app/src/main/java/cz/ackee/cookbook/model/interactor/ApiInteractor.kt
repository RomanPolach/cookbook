package cz.ackee.cookbook.model.interactor

import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.RateReceipeRequest
import cz.ackee.cookbook.model.api.Recipe

/**
 * Interactor that communicates with api
 */
interface ApiInteractor {

    suspend fun getRecipeListPaged(perPage: Int, offset: Int): List<Recipe>

    suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe

    suspend fun getRecipeDetailById(recipeId: String): Recipe

    suspend fun rateReceipeById(recipeId: String, ratingRequest: RateReceipeRequest): Recipe
}
