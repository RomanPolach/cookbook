package cz.ackee.cookbook.screens.addrecipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.repository.State
import cz.ackee.cookbook.model.validation.ValidationException
import cz.ackee.cookbook.screens.addrecipe.epoxy.recipeIngredient
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.extensions.rx.observeOnMainThread
import io.reactivex.rxkotlin.plusAssign
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk21.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Fragment for adding of new recipes
 */
class AddRecipeFragment : BaseFragment<AddRecipeLayout>() {

    private val viewModel: AddRecipeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return AddRecipeLayout(container!!.context).also { layout = it }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout.btnAdd.onClick {
            viewModel.onAddIngredient(layout.inputIngredient.editText!!.text.toString())
            layout.inputIngredient.editText!!.text.clear()
            refreshIngredientsList()
        }

        viewModel.observeState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> view.longSnackbar(R.string.general_sending)
                is State.Loaded -> {
                    view.longSnackbar(R.string.add_recipe_message_loaded_successfully)
                    activity!!.onBackPressed()
                }
                is State.Error -> handleErrors(state.error)
            }
        })
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

    fun handleErrors(error: Throwable) {
        if (error is ValidationException) {
            when (error.error) {
                ValidationException.ValidationErrorType.EMPTY_FIELD -> view?.longSnackbar(R.string.add_recipe_dialog_fill_all_fields)
            }
        } else {
            view?.longSnackbar(error.toString())
        }
    }

    private fun refreshIngredientsList() {
        layout.recyclerViewIngredients.buildModelsWith { controller ->
            with(controller) {
                viewModel.getIngredients().forEach {
                    recipeIngredient {
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
        viewModel.onSendRecipeClick(
            layout.inputRecipe.editText!!.text.toString(),
            layout.inputRecipeName.editText!!.text.toString(),
            layout.inputIntroText.editText!!.text.toString(),
            layout.inputTime.editText!!.text.toString())
    }

    override fun getTitle() = getString(R.string.add_recipe_toolbar_title)
}