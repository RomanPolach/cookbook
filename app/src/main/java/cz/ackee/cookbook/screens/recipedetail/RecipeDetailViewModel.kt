package cz.ackee.cookbook.screens.recipedetail

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import kotlinx.coroutines.launch

class RecipeDetailViewModel(val repository: RecipeRepository, val recipeId: String) : ScopedViewModel() {

    private val recipeDetailStateObserver = StateObserver<Recipe>()
    private val rateRecipeStateObserver = StateObserver<Recipe>()

    fun observeState() = recipeDetailStateObserver.observeState()
    fun observerRatingState() = rateRecipeStateObserver.observeState()

    init {
        launch {
            recipeDetailStateObserver.loading()
            try {
                repository.fetchRecipeDetail(recipeId)
            } catch (e: Exception) {
                recipeDetailStateObserver.error(e)
            }

            repository.getRecipeDetailObservable(recipeId)
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe({
                    recipeDetailStateObserver.loaded(it)
                }, {
                    recipeDetailStateObserver.error(it)
                })
        }
    }

    fun onUserRatingClick(rating: Float) {
        launch {
            rateRecipeStateObserver.loading()
            try {
                rateRecipeStateObserver.loaded(repository.rateRecipe(recipeId, rating))
            } catch (e: Exception) {
                rateRecipeStateObserver.error(e)
            }
        }
    }
}