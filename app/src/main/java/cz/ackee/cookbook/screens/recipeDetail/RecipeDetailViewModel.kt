package cz.ackee.cookbook.screens.recipeDetail

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

class RecipeDetailViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val recipeDetailStateObserver = StateObserver<Recipe>()
    private val rateRecipeStateObserver = StateObserver<Recipe>()

    fun observeState() = recipeDetailStateObserver.observeState()

    fun observerRatingState() = rateRecipeStateObserver.observeState()

    fun getRecipeDetail(recipeId: String) {
        launch {
            recipeDetailStateObserver.loading()
            try {
                recipeDetailStateObserver.loaded(repository.getRecipeDetail(recipeId))
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



