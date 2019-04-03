package cz.ackee.cookbook.screens.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.ackee.cookbook.R
import cz.ackee.cookbook.screens.addRecipe.AddRecipeViewModel
import cz.ackee.cookbook.screens.addRecipe.epoxy.addRecipe
import cz.ackee.cookbook.screens.base.fragment.BaseFragment
import cz.ackee.cookbook.screens.layout.AddRecipeLayout
import org.jetbrains.anko.sdk21.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Main app fragment with list of Recipes
 */

class AddRecipeFragment : BaseFragment<AddRecipeLayout>() {

    private val viewModel: AddRecipeViewModel by viewModel()

    override fun createLayout(parent: Context) = AddRecipeLayout(parent)

    private val ingredientsList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout.buttonAdd.onClick {
            ingredientsList.add(layout.inputIngredient.editText!!.text.toString())
            layout.inputIngredient.editText!!.text.clear()
            refreshIngredientsList()
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> Timber.d("TODO")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun getTitle() = getString(R.string.main_fragment_tooolbar_title)
}