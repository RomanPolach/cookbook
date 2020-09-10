package cz.ackee.cookbook.screens.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.RecipeRepository
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.viewmodel.ScopedViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import cz.ackee.extensions.rx.subscribeOnIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(val repository: RecipeRepository, val recipeId: String) : ScopedViewModel() {

    private val recipeDetailState = MutableLiveData<State<Recipe>>()
    private val ratingState = MutableLiveData<State<Recipe>>()
    private val ratingAllowedState = MutableLiveData<Boolean>()

    fun detailState(): MutableLiveData<State<Recipe>> = recipeDetailState
    fun ratingState(): MutableLiveData<State<Recipe>> = ratingState
    fun ratingAllowedState(): LiveData<Boolean> = ratingAllowedState

    init {
        launch {
            recipeDetailState.postValue(State.Loading)

            try {
                repository.fetchRecipeDetail(recipeId)
            } catch (e: Exception) {
                recipeDetailState.postValue(State.Error(e))
            }

            repository.getRecipeDetailObservable(recipeId)
                .subscribeOnIO()
                .observeOnMainThread()
                .subscribe {
                    recipeDetailState.postValue(State.Loaded(it.recipe))
                    ratingAllowedState.postValue(it.rated == null)
                }
        }
    }

    fun onUserRatingClick(rating: Float) {
        launch {
            ratingState.postValue(State.Loading)
            try {
                ratingState.postValue(State.Loaded(repository.rateRecipe(recipeId, rating)))
            } catch (e: Exception) {
                ratingState.postValue(State.Error(e))
            }
        }
    }
}