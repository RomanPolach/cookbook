package cz.ackee.cookbook.screens.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.addRecipe.AddRecipeFragment
import cz.ackee.cookbook.screens.addRecipe.RecipeDetailFragment
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.ListLayout
import cz.ackee.cookbook.screens.main.epoxy.recipe
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.withArguments
import cz.ackee.extensions.rx.observeOnMainThread
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.support.v4.dip
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main app fragment with list of Recipes
 */
class MainFragment : BaseFragment<ListLayout>() {

    private val viewModel: MainViewModel by viewModel()

    override fun createLayout(parent: Context) = ListLayout(parent, itemDecoration = RecyclerViewDivider.with(context!!)
        .asSpace()
        .color(color(R.color.divider))
        .size(dip(2))
        .hideLastDivider()
        .build())

    override fun ListLayout.viewCreated(savedState: Bundle?) {
        disposables += viewModel.observeState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Empty -> {
                        showProgress(false)
                        showEmpty(true)
                        showError(false)
                    }
                    is State.Loading -> {
                        showProgress(true)
                        showEmpty(false)
                        showError(false)
                    }
                    is State.Reloading -> {
                        showProgress(true)
                        showEmpty(false)
                        showError(false)
                        addRecipes(state.previousData)
                    }
                    is State.Loaded -> {
                        showProgress(false)
                        showEmpty(false)
                        showError(false)
                        addRecipes(state.data)
                    }
                    is State.Error -> {
                        showProgress(false)
                        showEmpty(false)
                        showError(true)
                        view.longSnackbar(state.error.localizedMessage)
                    }
                }
            }
    }

    private fun addRecipes(recipes: List<Recipe>?) {
        layout.epoxyRecyclerView.buildModelsWith { controller ->
            with(controller) {
                recipes?.forEach {
                    recipe {
                        onRecipeClick {
                            val bundle = Bundle().apply {
                                putString(RecipeDetailFragment.RECIPE_ID_KEY, it)
                            }
                            fragmentActivity?.replaceFragment(RecipeDetailFragment().withArguments(bundle))
                        }
                        recipeId(it.id)
                        id(it.id)
                        title(it.name)
                        score(it.score)
                        time("${it.duration} ${getString(R.string.main_fragment_minutes)}")
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> fragmentActivity?.replaceFragment(AddRecipeFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onInitActionBar(actionBar: ActionBar?, toolbar: Toolbar?) {
        super.onInitActionBar(actionBar, toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun getTitle() = getString(R.string.main_fragment_tooolbar_title)
}