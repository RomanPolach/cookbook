package cz.ackee.cookbook.screens.addRecipe

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.NewRecipeRequest
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.screens.addRecipe.epoxy.addRecipe
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.AddRecipeLayout
import cz.ackee.extensions.rx.observeOnMainThread
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk21.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main app fragment with list of Recipes
 */

class AddRecipeFragment : BaseFragment<AddRecipeLayout>() {

    private val viewModel: AddRecipeViewModel by viewModel()

    override fun createLayout(parent: Context) = AddRecipeLayout(parent)

    private val ingredientsList: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout.btnAdd.onClick {
            ingredientsList.add(layout.inputIngredient.editText!!.text.toString())
            layout.inputIngredient.editText!!.text.clear()
            refreshIngredientsList()
        }

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
                onSendRecipeClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    fun refreshIngredientsList() {
        layout.recyclerViewIngredients.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        layout.recyclerViewIngredients.buildModelsWith { controller ->
            with(controller) {
                ingredientsList.forEach {
                    addRecipe {
                        id(it)
                        ingredientTitle(it)
                    }
                }
            }
        }
    }

    override fun onInitActionBar(actionBar: ActionBar?, toolbar: Toolbar?) {
        super.onInitActionBar(actionBar, toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onSendRecipeClick() {
        hideIme()
        if (validate()) {
            val recipe = NewRecipeRequest(
                ingredients = ingredientsList,
                description = layout.inputRecipe.editText!!.text.toString(),
                name = "Ackee ${layout.inputRecipeName.editText!!.text}",
                duration = Integer.parseInt(layout.inputTime.editText!!.text.toString()),
                info = layout.inputIntroText.editText!!.text.toString()
            )
            viewModel.onSendRecipeClick(recipe)
        } else {
            view?.longSnackbar(R.string.add_recipe_dialog_fill_all_fields)
        }
    }

    private fun validate(): Boolean {
        return !layout.inputRecipe.editText!!.text.toString().isBlank() &&
            !layout.inputRecipeName.editText!!.text.toString().isBlank() &&
            !layout.inputIntroText.editText!!.text.toString().isBlank() &&
            !ingredientsList.isEmpty() &&
            !layout.inputTime.editText!!.text.toString().isBlank()
    }

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}