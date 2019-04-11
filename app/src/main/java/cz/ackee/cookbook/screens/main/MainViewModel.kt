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

    private val loadingStateObserver = StateObserver<Unit>()

    private var recipeListOffset = 0
    val RECIPE_LIST_PER_PAGE = 5

    init {
        recipeListStateObserver.loading()
        launch {
            disposables += repository.getRecipeListObservable()
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe({
                    recipeListOffset = it.size
                    recipeListStateObserver.loaded(it)
                }, {
                    recipeListStateObserver.error(it)
                })
        }
    }

    fun observeLoadingState() = loadingStateObserver.observeState()

    fun observeState() = recipeListStateObserver.observeState()

    fun onScrolledToEnd() {
        if (!repository.isAtTheEndofList()) {
            loadingStateObserver.loading()
            launch {
                try {
                    repository.fetchRecipeListPaged(RECIPE_LIST_PER_PAGE, recipeListOffset)
                    loadingStateObserver.loaded(Unit)
                } catch (e: Exception) {
                    recipeListStateObserver.error(e)
                    loadingStateObserver.loaded(Unit)
                }
            }
        }
    }
}
