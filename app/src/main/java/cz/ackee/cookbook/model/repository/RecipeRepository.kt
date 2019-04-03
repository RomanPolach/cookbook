package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.exception.ExceptionMapperHelper
import cz.ackee.cookbook.model.interactor.ApiInteractor

/**
 * Sample repository
 */
interface RecipeRepository {

    /**
     * Fetch fresh recipe data from API
     */
    suspend fun getRecipeList(): List<Recipe>
}

class RecipeRepositoryImpl(val apiInteractor: ApiInteractor) : RecipeRepository, ExceptionMapperHelper {

    suspend override fun getRecipeList(): List<Recipe> {
        return try {
            apiInteractor.getSampleData()
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }
}