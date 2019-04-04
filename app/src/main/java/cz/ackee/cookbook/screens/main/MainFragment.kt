package cz.ackee.cookbook.screens.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.ListLayout
import cz.ackee.extensions.android.color
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

    override fun getTitle() = getString(R.string.main_fragment_tooolbar_title)
}