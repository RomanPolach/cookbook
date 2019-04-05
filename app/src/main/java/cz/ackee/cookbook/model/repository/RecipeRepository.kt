package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.NewRecipeRequest
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
    suspend fun sendRecipe(recipeDescription: String, name: String, intro: String, time: String,
        ingredientsList: List<String>): Recipe
}

class RecipeRepositoryImpl(val apiInteractor: ApiInteractor) : RecipeRepository {

    suspend override fun getRecipeList(): List<Recipe> {
        return try {
            apiInteractor.getRecipeList()
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    suspend override fun sendRecipe(recipeDescription: String, name: String, intro: String, time: String,
        ingredientsList: List<String>): Recipe {
        val recipe = NewRecipeRequest(
            ingredients = ingredientsList,
            description = recipeDescription,
            name = "Ackee $name",
            duration = Integer.parseInt(time),
            info = intro
        )
        return try {
            apiInteractor.sendRecipe(recipe)
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }
}