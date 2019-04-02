package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.exception.ExceptionMapperHelper
import cz.ackee.cookbook.model.interactor.ApiInteractor
import kotlinx.coroutines.Deferred

/**
 * Sample repository
 */
interface RecipeRepository {

    /**
     * Fetch fresh recipe data from API
     */
    suspend fun fetchRecipeList(): Deferred<List<Recipe>>
}

class RecipeRepositoryImpl(val apiInteractor: ApiInteractor) : RecipeRepository, ExceptionMapperHelper {

    suspend override fun fetchRecipeList(): Deferred<List<Recipe>> {
        return apiInteractor.getSampleData()
    }
}