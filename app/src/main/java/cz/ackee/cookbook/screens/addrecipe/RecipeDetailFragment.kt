package cz.ackee.cookbook.screens.addrecipe

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.base.activity.FragmentActivity
import cz.ackee.cookbook.screens.base.activity.startFragmentActivity
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.recipedetail.RecipeDetailLayout
import cz.ackee.cookbook.screens.recipedetail.RecipeDetailViewModel
import cz.ackee.cookbook.screens.recipedetail.ingredientDetail
import cz.ackee.cookbook.utils.withModels
import cz.ackee.extensions.android.color
import kotlinx.android.synthetic.main.fragment_detail.*

import org.jetbrains.anko.design.longSnackbar
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Detail of Recipe
 */
class RecipeDetailFragment : BaseFragment<RecipeDetailLayout>() {

    private val viewModel: RecipeDetailViewModel by viewModel { parametersOf(arguments!!.getString(RECIPE_ID_KEY)) }

    override var provideToolbar: Boolean = true

    companion object {
        val RECIPE_ID_KEY = "recipe_id"

        fun arguments(recipeId: String) = bundleOf(RECIPE_ID_KEY to recipeId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailrecyclerview?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.detailState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loaded -> showRecipe(state.data)
                is State.Error -> view.longSnackbar(R.string.detail_loading_failed)
            }
        })

        viewModel.ratingState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loaded -> view.longSnackbar(R.string.recipe_detail_recipe_rated)
                is State.Error -> view.longSnackbar(R.string.recipe_detail_rating_failed)
            }
        })

        viewModel.ratingAllowedState().observe(viewLifecycleOwner, Observer { allowed ->
            ratingbar_rate?.isVisible = allowed
            view_rate?.isVisible = allowed
            text_rate_this?.isVisible = allowed
        })

        ratingbar_rate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.onUserRatingClick(rating)
        }
        setStatusBar(false)
    }

    private fun showRecipe(recipe: Recipe) {
        ratingbar?.rating = recipe.score
        txtTitle?.text = recipe.name
        text_intro?.text = recipe.description
        text_time?.text = "${recipe.duration} min."
        text_description?.text = recipe.description

        detailrecyclerview?.withModels {
            recipe.ingredients?.forEach {
                ingredientDetail {
                    id(it)
                    title(it)
                }
            }
        }
    }

    // Method that enables collapsing toolbar be drawn under status bar and change status bar icons
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

    override fun getTitle() = getString(R.string.add_recipe_recipe_hint)
}