package cz.ackee.cookbook.screens.recipeDetail

import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

class RecipeDetailViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val addRecipeStateObserver = StateObserver<Recipe>()

    fun observeState() = addRecipeStateObserver.observeState()

    fun onSendRecipeClick(recipe: NewRecipeRequest) {
        launch {
            addRecipeStateObserver.loading()
            try {
                addRecipeStateObserver.loaded(repository.sendRecipe(recipe))
            } catch (e: Exception) {
                addRecipeStateObserver.error(e)
            }
        }
    }
}

