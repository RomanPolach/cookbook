package cz.ackee.cookbook.model.repository

import cz.ackee.cookbook.model.api.*
import cz.ackee.cookbook.model.api.db.RecipeDao
import cz.ackee.cookbook.model.api.exception.resolveException
import cz.ackee.cookbook.model.interactor.ApiInteractor
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for recipes
 */
interface RecipeRepository {

    // Get obbservable for loading data
    suspend fun getRecipeListObservable(): Flowable<List<Recipe>>

    //rate Recipe
    suspend fun rateRecipe(recipeId: String, rating: Float): Recipe

    // send new Recipe
    suspend fun sendRecipe(recipeDescription: String, name: String, intro: String, time: String,
        ingredientsList: List<String>): Recipe

    // get observable loading detail of recipe
    suspend fun getRecipeDetailObservable(recipeId: String): Flowable<RecipeDetail>

    //fetch data from remote repository
    suspend fun fetchRecipeDetail(recipeId: String)

    //fetch all recipes from internet
    suspend fun fetchRecipeList()

    suspend fun fetchRecipeListPaged()

    fun isAtTheEndofList(): Boolean
}

class RecipeRepositoryImpl(val apiInteractor: ApiInteractor, val recipeDao: RecipeDao) : RecipeRepository {
    var endOfRecipesListReached = false
    private var recipeListOffset = 0
    val RECIPE_LIST_PER_PAGE = 5

    suspend override fun getRecipeListObservable(): Flowable<List<Recipe>> {
        return recipeDao.getRecipesPaged(RECIPE_LIST_PER_PAGE, recipeListOffset)
    }

    suspend override fun fetchRecipeList() {
        try {
            val recipes = apiInteractor.getRecipeList()
            withContext(Dispatchers.IO) {
                recipeDao.insertAllRecipes(recipes)
            }
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    suspend override fun fetchRecipeListPaged() {

        if (!endOfRecipesListReached) {
            try {
                val recipes = apiInteractor.getRecipeListPaged(RECIPE_LIST_PER_PAGE, recipeListOffset)
                if (recipes.size < RECIPE_LIST_PER_PAGE) {
                    endOfRecipesListReached = true
                } else {
                    recipeListOffset += recipes.size
                }
                withContext(Dispatchers.IO) {
                    recipeDao.insertAllRecipes(recipes)
                }
            } catch (e: Exception) {
                throw resolveException(e)
            }
        }
    }

    override fun isAtTheEndofList(): Boolean {
        return endOfRecipesListReached
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

    suspend override fun getRecipeDetailObservable(recipeId: String): Flowable<RecipeDetail> {
        return recipeDao.getRecipeDetail(recipeId)
    }

    suspend override fun fetchRecipeDetail(recipeId: String) {
        try {
            val newRecipe = apiInteractor.getRecipeDetailById(recipeId)
            withContext(Dispatchers.IO) {
                recipeDao.insertDetail(newRecipe)
            }
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }

    override suspend fun rateRecipe(recipeId: String, rating: Float): Recipe {
        val ratingRequest = RateReceipeRequest(rating)
        try {
            val response = apiInteractor.rateReceipeById(recipeId, ratingRequest)
            withContext(Dispatchers.IO) {
                recipeDao.insertRecipeVoted(RatedRecipes(recipeId, true))
            }
            return response
        } catch (e: Exception) {
            throw resolveException(e)
        }
    }
}