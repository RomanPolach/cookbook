package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.RecipeDetailLayout
import cz.ackee.cookbook.screens.recipeDetail.RecipeDetailViewModel
import cz.ackee.extensions.rx.observeOnMainThread
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Detail of Recipe
 */
class RecipeDetailFragment : BaseFragment<RecipeDetailLayout>() {

    private val viewModel: RecipeDetailViewModel by viewModel()

    override var provideToolbar: Boolean = true

    companion object {
        val RECIPE_ID_KEY = "recipe_id"
    }

    override fun createLayout(parent: Context) = RecipeDetailLayout(parent)

    override fun RecipeDetailLayout.viewCreated(savedState: Bundle?) {
        viewModel.getRecipeDetail(arguments!!.getString(RECIPE_ID_KEY)!!)

        disposables += viewModel.observeState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Loaded -> {
                        showRecipeDetail(state.data)
                    }
                    is State.Error -> {
                        view.longSnackbar(state.error.localizedMessage)
                    }
                }
            }
    }

    private fun showRecipeDetail(recipe: Recipe) {
        with(layout) {
            txtRecipeTitle.text = recipe.name
            txtRecipeDescription.text = recipe.description
            txtRecipeIntro.text = recipe.description
            txtTime.text = "${recipe.duration} ${getString(R.string.main_fragment_minutes)}"
            scoreRatingBar.rating = recipe.score
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}