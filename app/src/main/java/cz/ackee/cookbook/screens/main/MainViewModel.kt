package cz.ackee.cookbook.screens.main

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.db.RecipeDao
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import io.reactivex.rxkotlin.plusAssign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model for main screen
 */
class MainViewModel(val repository: RecipeRepository, val recipeDao: RecipeDao) : ScopedViewModel() {

    private val recipeListStateObserver = StateObserver<List<Recipe>>()

    init {
        recipeListStateObserver.loading()
        launch {
            disposables += recipeDao.getRecipes()
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe {
                    if (it.isNotEmpty()) {
                        recipeListStateObserver.loaded(it)
                    }
                }

            try {
                val recipes = repository.getRecipeList()
                withContext(context = Dispatchers.Default) {
                    recipeDao.insertAllRecipes(recipes)
                }
            } catch (e: Exception) {
                recipeListStateObserver.error(e)
            }
        }
    }

    fun observeState() = recipeListStateObserver.observeState()
}
