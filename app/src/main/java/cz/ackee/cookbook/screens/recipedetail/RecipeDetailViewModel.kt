package cz.ackee.cookbook.screens.recipedetail

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(val repository: RecipeRepository, val recipeId: String) : ScopedViewModel() {

    private val recipeDetailStateObserver = StateObserver<Recipe>()
    private val ratingStateObserver = StateObserver<Recipe>()
    private val ratingAllowedObserver = StateObserver<Boolean>()

    fun observeState() = recipeDetailStateObserver.observeState()
    fun observerRatingState() = ratingStateObserver.observeState()
    fun observeRatingAllowedState() = ratingAllowedObserver.observeState()

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
                    recipeDetailStateObserver.loaded(it.recipe)
                    ratingAllowedObserver.loaded(it.rated == null)
                }, {
                    recipeDetailStateObserver.error(it)
                })
        }
    }

    fun onUserRatingClick(rating: Float) {
        launch {
            ratingStateObserver.loading()
            try {
                // wrap do RecipeDetail which saves state of rating, so we can hide rating score bar in voted recipes
                ratingStateObserver.loaded(repository.rateRecipe(recipeId, rating))
                withContext(Dispatchers.IO) {
                    ratingAllowedObserver.loaded(true)
                }
            } catch (e: Exception) {
                ratingStateObserver.error(e)
            }
        }
    }
}