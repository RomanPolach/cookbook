package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.RateReceipeRequest
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.exception.resolveException
import cz.ackee.cookbook.model.interactor.ApiInteractor

/**
 * Repository for recipes
 */
interface RecipeRepository {

    //Fetch fresh recipe data from API
    suspend fun getRecipeList(): List<Recipe>

    // send new recipe
    suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe

    // get Recipe detail
    suspend fun getRecipeDetail(recipeId: String): Recipe

    //rate Recipe
    suspend fun rateRecipe(recipeId: String, rating: Float): Recipe
}

class RecipeRepositoryImpl(val apiInteractor: ApiInteractor) : RecipeRepository {

    suspend override fun getRecipeList(): List<Recipe> {
        return try {
            apiInteractor.getSampleData()
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    suspend override fun sendRecipe(recipe: NewRecipeRequest): Recipe {
        return try {
            apiInteractor.sendRecipe(recipe)
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    suspend override fun getRecipeDetail(recipeId: String): Recipe {
        return try {
            apiInteractor.getRecipeDetailById(recipeId)
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    override suspend fun rateRecipe(recipeId: String, rating: Float): Recipe {
        val ratingRequest = RateReceipeRequest(rating)
        return try {
            apiInteractor.rateReceipeById(recipeId, ratingRequest)
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }
}