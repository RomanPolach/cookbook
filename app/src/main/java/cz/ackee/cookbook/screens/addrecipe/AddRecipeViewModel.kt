package cz.ackee.cookbook.screens.addrecipe

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.model.validation.ValidationException
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

/**
 * View model for AddRecipeFragment
 */
class AddRecipeViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val ingredientsList: MutableList<String> = mutableListOf()
    private val addRecipeStateObserver = StateObserver<Recipe>()

    fun observeState() = addRecipeStateObserver.observeState()

    fun onSendRecipeClick(recipe: String, name: String, intro: String, time: String) {
        val validationError = validate(recipe, name, intro, time)
        if (validationError == null) {
            launch {
                addRecipeStateObserver.loading()
                try {
                    addRecipeStateObserver.loaded(repository.sendRecipe(recipe, name, intro, time, ingredientsList))
                } catch (e: Exception) {
                    addRecipeStateObserver.error(e)
                }
            }
        } else {
            addRecipeStateObserver.error(validationError)
        }
    }

    fun onAddIngredient(ingredient: String) {
        ingredientsList.add(ingredient)
    }

    fun getIngredients() = ingredientsList

    private fun validate(recipe: String, name: String, intro: String, time: String): ValidationException? {
        if (!recipe.isBlank() &&
            !name.isBlank() &&
            !intro.isBlank() &&
            !time.isEmpty() &&
            !ingredientsList.isEmpty()) {
            return null
        } else {
            return (ValidationException(ValidationException.ValidationErrorType.EMPTY_FIELD))
        }
    }
}
