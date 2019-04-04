package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.AddRecipeLayout
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar

/**
 * Main app fragment with list of Recipes
 */

class RecipeDetailFragment : BaseFragment<AddRecipeLayout>() {

    private val viewModel: RecipeDetailViewModel by viewModel()

    override fun createLayout(parent: Context) = RecipeDetailLayout(parent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposables += viewModel.observeState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Loaded -> {
                        view.longSnackbar(R.string.add_recipe_message_loaded_successfully)
                        activity!!.onBackPressed()
                    }
                    is State.Error -> {
                        view.longSnackbar(state.error.toString())
                    }
                }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onInitActionBar(actionBar: ActionBar?, toolbar: Toolbar?) {
        super.onInitActionBar(actionBar, toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}