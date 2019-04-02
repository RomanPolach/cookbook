package cz.ackee.cookbook.screens.main

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

/**
 * View model for main screen
 */
class MainViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val recipeListStateObserver = StateObserver<List<Recipe>>()

    init {
        launch {
            recipeListStateObserver.loading()
            try {
                recipeListStateObserver.loaded(repository.fetchRecipeList().await())
            } catch (e: Exception) {
                recipeListStateObserver.error(e)
            }
        }
    }

    fun observeState() = recipeListStateObserver.observeState()
}
