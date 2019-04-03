package cz.ackee.cookbook.screens.addRecipe.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.google.android.material.textfield.TextInputLayout
import cz.ackee.ankoconstraintlayout.constraintLayout
import cz.ackee.cookbook.R
import cz.ackee.cookbook.utils.medium
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout

/**
 * input line for ingredients in AddRecipeFragment
 */

open class AddRecipeEpoxyModel : EpoxyModelWithLayout<AddRecipeEpoxyLayout>() {

    @EpoxyAttribute
    lateinit var ingredientTitle: String

    override fun createViewLayout(parent: ViewGroup): AddRecipeEpoxyLayout = AddRecipeEpoxyLayout(parent)

    override fun AddRecipeEpoxyLayout.bind() {
        ingredientText.editText!!.setText(ingredientTitle)
    }
}

class AddRecipeEpoxyLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var ingredientText: TextInputLayout

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                id = R.id.constraint_layout
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                verticalPadding = dip(8)

                ingredientText = textInputLayout {
                    isHintEnabled = false
                    isClickable = false

                    textInputEditText {
                        isEnabled = false
                        isFocusable = false
                        typeface = medium
                        padding = dip(16)
                    }
                }.lparams(width = matchParent, height = wrapContent)

                constraints {
                    ingredientText.connect(
                        TOPS of parentId with dip(16)
                    )
                }
            }
        }
    }
}
