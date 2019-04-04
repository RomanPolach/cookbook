package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import org.jetbrains.anko.design.themedTextInputLayout
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * UI component of toolbar
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
                    id = R.id.constraint_layout
                    layoutParams = ViewGroup.LayoutParams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                    verticalPadding = dip(15)
                    horizontalPadding = dip(15)

                    inputRecipeName = themedTextInputLayout(R.style.TextInputLayoutAppearance) {
                        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)
                        hint = string(R.string.add_recipe_input_recipe_name)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                    }

                    inputIntroText = themedTextInputLayout(R.style.TextInputLayoutAppearance) {
                        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)
                        hint = string(R.string.add_recipe_input_intro_text)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                        bottomMargin = dip(26)
                    }

                    titleTextView {
                        text = string(R.string.add_recipe_ingredients_title)
                        allCaps = true
                    }.lparams() {
                        topMargin = dip(10)
                    }

                    recyclerViewIngredients = epoxyRecyclerView {
                    }.lparams(width = matchParent, height = wrapContent)

                    inputIngredient = themedTextInputLayout(R.style.TextInputLayoutAppearance) {
                        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)
                        hint = string(R.string.add_recipe_your_ingredients_hint)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
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

                    inputRecipe = themedTextInputLayout(R.style.TextInputLayoutAppearance) {
                        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)
                        hint = string(R.string.add_recipe_recipe_hint)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                        }
                    }.lparams(width = org.jetbrains.anko.matchParent) {
                        topMargin = dip(26)
                    }

                    inputTime = themedTextInputLayout(R.style.TextInputLayoutAppearance) {
                        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)
                        hint = string(R.string.add_recipe_time_hint)

                        textInputEditText {
                            typeface = medium
                            padding = dip(16)
                            inputType = InputType.TYPE_CLASS_NUMBER
                        }
                    }.lparams(width = dip(200)) {
                        topMargin = dip(26)
                    }
                }
            }
        }
    }
}