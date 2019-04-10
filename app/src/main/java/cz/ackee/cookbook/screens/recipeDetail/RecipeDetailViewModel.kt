package cz.ackee.cookbook.screens.recipeDetail

import cz.ackee.cookbook.model.api.RatedRecipes
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.db.RecipeDao
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(val repository: RecipeRepository, val recipeDao: RecipeDao) : ScopedViewModel() {

    private val recipeDetailStateObserver = StateObserver<Recipe>()
    private val ratingStateObserver = StateObserver<Recipe>()
    private val ratingAllowedObserver = StateObserver<Boolean>()

    fun observeState() = recipeDetailStateObserver.observeState()
    fun observerRatingState() = ratingStateObserver.observeState()
    fun observeRatingAllowedState() = ratingAllowedObserver.observeState()

    fun getRecipeDetail(recipeId: String) {
        launch {
            recipeDetailStateObserver.loading()
            recipeDao.getRecipeDetail(recipeId)
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe {
                    recipeDetailStateObserver.loaded(it.recipe)
                    //if there is a record in database about this recipe voted, disable rating bar
                    ratingAllowedObserver.loaded(it.rated == null)
                }

            try {
                val newRecipe = repository.getRecipeDetail(recipeId)
                withContext(Dispatchers.IO) {
                    recipeDao.insertDetail(newRecipe)
                }
            } catch (e: Exception) {
                recipeDetailStateObserver.error(e)
            }
        }
    }

    fun rateRecipe(recipeId: String, rating: Float) {
        launch {
            ratingStateObserver.loading()
            try {
                // wrap do RecipeDetail which saves state of rating, so we can hide rating score bar in voted recipes
                ratingStateObserver.loaded(repository.rateRecipe(recipeId, rating))
                withContext(Dispatchers.IO) {
                    recipeDao.insertRecipeVoted(RatedRecipes(recipeId, true))
                    ratingAllowedObserver.loaded(true)
                }
            } catch (e: Exception) {
                ratingStateObserver.error(e)
            }
        }
    }

    fun onUserRatingClick(recipeId: String, rating: Float) {
        // run request only once
        if (ratingStateObserver.getCurrentState() !is State.Loading && ratingStateObserver.getCurrentState() !is State.Loaded) {
            rateRecipe(recipeId, rating)
        }
    }
}



