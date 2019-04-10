package cz.ackee.cookbook.screens.recipeDetail

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
    private val rateRecipeStateObserver = StateObserver<Recipe>()

    fun observeState() = recipeDetailStateObserver.observeState()
    fun observerRatingState() = rateRecipeStateObserver.observeState()

    fun getRecipeDetail(recipeId: String) {
        launch {
            recipeDetailStateObserver.loading()
            recipeDao.getRecipeById(recipeId)
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe {
                    recipeDetailStateObserver.loaded(it)
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
            rateRecipeStateObserver.loading()
            try {
                rateRecipeStateObserver.loaded(repository.rateRecipe(recipeId, rating))
                withContext(Dispatchers.IO) {
                    // TODO save to DB recipeDao.setUserVoted(recipeId, true)
                }
            } catch (e: Exception) {
                rateRecipeStateObserver.error(e)
            }
        }
    }

    fun onUserRatingClick(recipeId: String, rating: Float) {
        // run request only once
        if (rateRecipeStateObserver.getCurrentState() !is State.Loading && rateRecipeStateObserver.getCurrentState() !is State.Loaded) {
            rateRecipe(recipeId, rating)
        }
    }
}



