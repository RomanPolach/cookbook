package cz.ackee.cookbook.screens.recipeDetail

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

class RecipeDetailViewModel(val repository: RecipeRepository, val recipeId: String) : ScopedViewModel() {

    private val getRecipeDetailStateObserver = StateObserver<Recipe>()

    fun observeState() = getRecipeDetailStateObserver.observeState()

    fun getRecipeDetail(recipeId: String) {
        launch {
            getRecipeDetailStateObserver.loading()
            try {
                getRecipeDetailStateObserver.loaded(repository.getRecipeDetail(recipeId))
            } catch (e: Exception) {
                getRecipeDetailStateObserver.error(e)
            }
        }
    }
}



