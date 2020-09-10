package cz.ackee.cookbook.screens.addrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.model.validation.ValidationException
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

/**
 * View model for AddRecipeFragment
 */
class AddRecipeViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    val ingredientsList: MutableList<String> = mutableListOf()
    private val addRecipeStateObserver = MutableLiveData<State<Recipe>>()

    fun recipeState(): LiveData<State<Recipe>> = addRecipeStateObserver

    fun onSendRecipeClick(recipe: String, name: String, intro: String, time: String) {
        val validationError = validate(recipe, name, intro, time)
        if (validationError == null) {
            launch {
                addRecipeStateObserver.postValue(State.Loading)
                try {
                    addRecipeStateObserver.postValue(State.Loaded(repository.sendRecipe(recipe, name, intro, time, ingredientsList)))
                } catch (e: Exception) {
                    addRecipeStateObserver.postValue(State.Error(e))
                }
            }
        } else {
            addRecipeStateObserver.postValue(State.Error(validationError))
        }
    }

    fun onAddIngredient(ingredient: String) {
        ingredientsList.add(ingredient)
    }

    private fun validate(recipe: String, name: String, intro: String, time: String): ValidationException? {
        return if (!recipe.isBlank() &&
            !name.isBlank() &&
            !intro.isBlank() &&
            time.isNotEmpty() &&
            ingredientsList.isNotEmpty()) {
            null
        } else {
            (ValidationException(ValidationException.ValidationErrorType.EMPTY_FIELD))
        }
    }
}
