package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.exception.resolveException
import cz.ackee.cookbook.model.interactor.ApiInteractor

/**
 * Sample repository
 */
interface RecipeRepository {

    //Fetch fresh recipe data from API
    suspend fun getRecipeList(): List<Recipe>

    // send new recipe
    suspend fun sendRecipe(recipe: NewRecipeRequest): Recipe
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
}