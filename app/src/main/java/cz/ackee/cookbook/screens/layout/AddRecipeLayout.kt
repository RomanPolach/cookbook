package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.textfield.TextInputLayout
import cz.ackee.cookbook.R
import cz.ackee.cookbook.utils.epoxyRecyclerView
import cz.ackee.cookbook.utils.medium
import cz.ackee.cookbook.utils.titleTextView
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.drawable
import cz.ackee.extensions.android.drawableLeft
import cz.ackee.extensions.android.string
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * UI component of toolbar
 */
class AddRecipeLayout(parent: Context) : ViewLayout(parent) {

    lateinit var inputRecipeName: TextInputLayout

    lateinit var inputIntroText: TextInputLayout

    lateinit var txtIngredient: TextView

    lateinit var recyclerViewIngredients: EpoxyRecyclerView

    lateinit var inputIngredient: TextInputLayout

    lateinit var buttonAdd: Button

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            nestedScrollView {
                verticalLayout {
                    layoutParams = ViewGroup.LayoutParams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                    id = R.id.constraint_layout

                    inputRecipeName = textInputLayout {
                        hint = string(R.string.add_recipe_input_recipe_name)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                        bottomMargin = dip(26)
                    }

                    inputIntroText = textInputLayout {
                        hint = string(R.string.add_recipe_input_intro_text)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                        bottomMargin = dip(26)
                    }

                    txtIngredient = titleTextView {
                        text = string(R.string.add_recipe_ingredients_title)
                    }.lparams() {
                        topMargin = dip(10)
                        bottomMargin = dip(10)
                    }

                    recyclerViewIngredients = epoxyRecyclerView {
                    }.lparams(width = matchParent, height = wrapContent)

                    inputIngredient = textInputLayout {
                        hint = string(R.string.add_recipe_your_ingredients_hint)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                        bottomMargin = dip(26)
                    }

                    buttonAdd = button {
                        textColor = color(R.color.button_pink)
                        drawableLeft = R.drawable.ic_add_small
                        background = drawable(R.drawable.rounded_button_shape)
                        text = string(R.string.add_recipe_button_add_title)
                    }.lparams(width = wrapContent) {
                        gravity = Gravity.CENTER
                    }
                }
            }
        }
    }
}