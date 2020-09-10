package cz.ackee.cookbook.screens.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
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
import cz.ackee.cookbook.screens.main.epoxy.progress
import cz.ackee.cookbook.screens.main.epoxy.recipe
import cz.ackee.extensions.android.color
import cz.ackee.extensions.epoxy.adapterProperty
import cz.ackee.extensions.rx.observeOnMainThread
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.support.v4.dip
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main app fragment with list of Recipes
 */
class MainFragment : BaseFragment<ListLayout>() {

    private val viewModel: MainViewModel by viewModel()

    private val recipesController = object : EpoxyController() {
        var recipes: List<Recipe> by adapterProperty(emptyList())

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_layout, container, false)
    }

    fun addEpoxyDivider() {
        epoxyRecyclerView.addItemDecoration(RecyclerViewDivider.with(requireContext())
            .inset(dip(16), dip(16))
            .size(dip(2))
            .color(Color.BLACK)
            .build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epoxyRecyclerView.setController(recipesController)
        addEpoxyDivider()
        viewModel.observeState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Empty -> {
                    showProgress(false)
                    showEmptyLayout(true)
                }
                is State.Loading -> {
                    showProgress(true)
                    showEmptyLayout(false)
                }
                is State.Loaded -> {
                    showProgress(false)
                    showEmptyLayout(false)
                    addRecipes(state.data)
                }
                is State.Error -> {
                    showProgress(false)
                    showEmptyLayout(false)
                    view.longSnackbar(state.error.toString())
                }
            }
        })
    }

    private fun addRecipes(recipes: List<Recipe>) {
        recipesController.recipes = recipes
    }

    fun showProgress(show: Boolean) {
        progressBar.isVisible = show
    }

    fun showEmptyLayout(show: Boolean) {
        empty_layout.isVisible = show
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