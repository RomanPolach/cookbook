package cz.ackee.cookbook.screens.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyController
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.addrecipe.AddRecipeFragment
import cz.ackee.cookbook.screens.addrecipe.RecipeDetailFragment
import cz.ackee.cookbook.screens.base.activity.FragmentActivity
import cz.ackee.cookbook.screens.base.activity.startFragmentActivity
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.ListLayout
import cz.ackee.cookbook.screens.main.epoxy.recipe
import cz.ackee.extensions.android.color
import cz.ackee.extensions.epoxy.adapterProperty
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

    private val recipesController = object : EpoxyController() {
        var recipes: List<Recipe> by adapterProperty(listOf())

        override fun buildModels() {
            recipes.forEach {
                recipe {
                    id(it.id)
                    onRecipeClick {
                        startFragmentActivity<FragmentActivity>(RecipeDetailFragment::class.java.name, provideToolbar = false,
                            fragmentArgs = RecipeDetailFragment.arguments(it))
                    }
                    recipeItem(it)
                }
            }
        }
    }

    override fun createLayout(parent: Context) =
        ListLayout(parent, controller = recipesController, itemDecoration = RecyclerViewDivider.with(context!!)
            .asSpace()
            .color(color(R.color.divider))
            .size(dip(2))
            .hideLastDivider()
            .build(), onScrolledToEnd = {
            viewModel.onScrolledToEnd()
        })

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
                        addRecipes(state.previousData ?: emptyList())
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
                        view.longSnackbar(state.error.toString())
                    }
                }
            }
    }

    private fun addRecipes(recipes: List<Recipe>) {
        recipesController.recipes = recipes
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