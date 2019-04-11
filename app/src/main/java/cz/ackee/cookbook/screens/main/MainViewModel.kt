package cz.ackee.cookbook.screens.main

import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.StateObserver
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import io.reactivex.rxkotlin.plusAssign
import kotlinx.coroutines.launch

/**
 * View model for main screen
 */
class MainViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val recipeListStateObserver = StateObserver<List<Recipe>>()

    init {
        recipeListStateObserver.loading()
        launch {
            disposables += repository.getRecipeListObservable()
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe({
                    recipeListStateObserver.loaded(it)
                }, {
                    recipeListStateObserver.error(it)
                })
            try {
                repository.fetchRecipeListPaged()
            } catch (e: Exception) {
                recipeListStateObserver.error(e)
            }
        }
    }

    fun observeState() = recipeListStateObserver.observeState()

    fun onScrolledToEnd() {
        if (!repository.isAtTheEndofList()) {
            recipeListStateObserver.loading()
            launch {
                try {
                    repository.fetchRecipeListPaged()
                } catch (e: Exception) {
                    recipeListStateObserver.error(e)
                }
            }
        }
    }
}
