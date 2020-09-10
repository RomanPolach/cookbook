package cz.ackee.cookbook.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import io.reactivex.rxkotlin.plusAssign
import kotlinx.coroutines.launch

/**
 * View model for main screen
 */
class MainViewModel(val repository: RecipeRepository) : ScopedViewModel() {

    private val recipeListStateObserver = MutableLiveData<State<List<Recipe>>>()

    init {
        recipeListStateObserver.postValue(State.Loading)

        disposables += repository.getRecipeListObservable()
            .subscribeOnIO()
            .observeOnMainThread()
            .subscribe({
                if (it.isEmpty()) {
                    fetchRecipes()
                } else {
                    recipeListStateObserver.postValue(State.Loaded(it))
                }
            }, {
                recipeListStateObserver.postValue(State.Error(it))
            })
    }

    fun observeState(): LiveData<State<List<Recipe>>> = recipeListStateObserver

    fun fetchRecipes() {
        launch {
            try {
                repository.fetchRecipeList()
            } catch (e: Exception) {
                recipeListStateObserver.postValue(State.Error((e)))
            }
        }
    }
}