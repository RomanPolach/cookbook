package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.view.MenuItem
import cz.ackee.cookbook.R
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.RecipeDetailLayout
import cz.ackee.cookbook.screens.recipeDetail.RecipeDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Detail of Recipe
 */
class RecipeDetailFragment : BaseFragment<RecipeDetailLayout>() {

    private val viewModel: RecipeDetailViewModel by viewModel()

    override var provideToolbar: Boolean = true

    override fun createLayout(parent: Context) = RecipeDetailLayout(parent)



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun RecipeDetailLayout.view


    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}