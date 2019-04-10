package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.activity.FragmentActivity
import cz.ackee.cookbook.screens.base.activity.startFragmentActivity
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.RecipeDetailLayout
import cz.ackee.cookbook.screens.recipeDetail.RecipeDetailViewModel
import cz.ackee.cookbook.screens.recipeDetail.ingredientDetail
import cz.ackee.cookbook.utils.withModels
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.visible
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
        setStatusBar(false)
        viewModel.getRecipeDetail(arguments!!.getString(RECIPE_ID_KEY)!!)
        scoreBottomRatingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            viewModel.onUserRatingClick(arguments!!.getString(RECIPE_ID_KEY)!!, rating)
        }

        disposables += viewModel.observeState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Loaded -> {
                        showRecipeDetail(state.data)
                    }
                    is State.Error -> {
                        view.longSnackbar(state.error.toString())
                    }
                }
            }

        disposables += viewModel.observerRatingState()
            .observeOnMainThread()
            .subscribe { state ->
                when (state) {
                    is State.Loading -> view.longSnackbar(R.string.sending)
                    is State.Loaded -> {
                        view.longSnackbar(R.string.recipe_detail_recipe_rated)
                    }
                    is State.Error -> {
                        view.longSnackbar(state.error.toString())
                    }
                }
            }

        disposables += viewModel.observeRatingAllowedState()
            .observeOnMainThread()
            .subscribe { state ->
                if (state is State.Loaded) {
                    if (!state.data) {
                        layout.scoreBottomRatingBar.visible = false
                    }
                }
            }
    }

    private fun setStatusBar(showDarkIcons: Boolean) {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        activity!!.window.statusBarColor = color(R.color.semitransparent_status_bar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val visibility = activity!!.window.decorView.systemUiVisibility
            if (showDarkIcons) {
                activity!!.window.decorView.systemUiVisibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                activity!!.window.decorView.systemUiVisibility = visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = activity!!.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun showRecipeDetail(recipe: Recipe) {
        with(layout) {
            txtRecipeTitle.text = recipe.name
            txtRecipeDescription.text = recipe.description
            txtRecipeIntro.text = recipe.description
            txtTime.text = "${recipe.duration} ${getString(R.string.main_fragment_minutes)}"
            scoreRatingBar.rating = recipe.score

            recyclerViewIngredients.withModels {
                recipe.ingredients?.forEach {
                    ingredientDetail {
                        id(it)
                        title(it!!)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> {
                startFragmentActivity<FragmentActivity>(AddRecipeFragment::class.java.name, provideToolbar = true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_white, menu)
    }

    override fun onInitActionBar(actionBar: ActionBar?, toolbar: Toolbar?) {
        super.onInitActionBar(actionBar, toolbar)
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}