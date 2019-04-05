package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.textfield.TextInputLayout
import cz.ackee.cookbook.R
import cz.ackee.cookbook.utils.defaultTextInputLayout
import cz.ackee.cookbook.utils.epoxyRecyclerView
import cz.ackee.cookbook.utils.titleTextView
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.drawable
import cz.ackee.extensions.android.drawableLeft
import cz.ackee.extensions.android.string
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Layout for AddRecipeFragment
 */
class AddRecipeLayout(parent: Context) : ViewLayout(parent) {

    lateinit var inputRecipeName: TextInputLayout
    lateinit var inputIntroText: TextInputLayout
    lateinit var recyclerViewIngredients: EpoxyRecyclerView
    lateinit var inputIngredient: TextInputLayout
    lateinit var btnAdd: Button
    lateinit var inputRecipe: TextInputLayout
    lateinit var inputTime: TextInputLayout

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            nestedScrollView {
                verticalLayout {
                    id = R.id.linear_layout
                    padding = dip(15)

                    inputRecipeName = defaultTextInputLayout {
                        hint = string(R.string.add_recipe_input_recipe_name)
                    }.lparams(width = matchParent) {
                        topMargin = dip(26)
                    }

                    inputIntroText = defaultTextInputLayout {
                        hint = string(R.string.add_recipe_input_intro_text)
                    }.lparams(width = matchParent) {
                        verticalMargin = dip(26)
                    }

                    titleTextView {
                        text = string(R.string.add_recipe_ingredients_title)
                        allCaps = true
                    }.lparams {
                        topMargin = dip(10)
                    }

                    recyclerViewIngredients = epoxyRecyclerView {
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams(width = matchParent, height = wrapContent)

                    inputIngredient = defaultTextInputLayout {
                        hint = string(R.string.add_recipe_your_ingredients_hint)
                    }.lparams(width = matchParent) {
                        topMargin = dip(26)
                    }

                    btnAdd = button {
                        textColor = color(R.color.button_pink)
                        drawableLeft = R.drawable.ic_add_small
                        background = drawable(R.drawable.rounded_button_shape)
                        text = string(R.string.add_recipe_button_add_title)
                    }.lparams(width = wrapContent) {
                        topMargin = dip(20)
                    }

                    inputRecipe = defaultTextInputLayout {
                        hint = string(R.string.add_recipe_recipe_hint)
                    }.lparams(width = matchParent) {
                        topMargin = dip(26)
                    }

                    linearLayout {
                        inputTime = defaultTextInputLayout {
                            hint = string(R.string.add_recipe_time_hint)

                            with(editText!!) {
                                inputType = InputType.TYPE_CLASS_NUMBER
                            }
                        }.lparams(width = wrapContent, weight = 0.4f) {
                            topMargin = dip(26)
                        }

                        space().lparams(width = 0, weight = 0.6f)
                    }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}