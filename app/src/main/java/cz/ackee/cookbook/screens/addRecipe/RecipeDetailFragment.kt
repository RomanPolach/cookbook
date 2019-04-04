package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar(true)

//        disposables += viewModel.observeState()
//            .observeOnMainThread()
//            .subscribe { state ->
//                when (state) {
//                    is State.Loaded -> {
//                        view.longSnackbar(R.string.add_recipe_message_loaded_successfully)
//                        activity!!.onBackPressed()
//                    }
//                    is State.Error -> {
//                        view.longSnackbar(state.error.toString())
//                    }
//                }
//            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setStatusBar(showDarkIcons: Boolean) {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            activity!!.window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val visibility = activity!!.window.decorView.systemUiVisibility
            if (showDarkIcons) {
                activity?.window?.decorView?.systemUiVisibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                activity?.window?.decorView?.systemUiVisibility = visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
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

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}