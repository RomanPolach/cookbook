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
    // boolean value true indicates, it is last page
    private val loadingStateObserver = StateObserver<Boolean>()
    private var recipeListOffset = 0

    init {
        recipeListStateObserver.loading()
        launch {
            disposables += repository.getRecipeListObservable()
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe({
                    if (it.isEmpty()) {
                        fetchMoreRecipes()
                    } else {
                        recipeListOffset = it.size
                        recipeListStateObserver.loaded(it)
                    }
                }, {
                    recipeListStateObserver.error(it)
                })
        }
    }

    fun observeLoadingState() = loadingStateObserver.observeState()

    fun observeState() = recipeListStateObserver.observeState()

    fun fetchMoreRecipes() {
        loadingStateObserver.loading()
        launch {
            try {
                repository.fetchRecipeListPaged(recipeListOffset)
                loadingStateObserver.loaded(repository.isAtTheEndOfList())
            } catch (e: Exception) {
                recipeListStateObserver.error(e)
                loadingStateObserver.loaded(repository.isAtTheEndOfList())
            }
        }
    }
}
