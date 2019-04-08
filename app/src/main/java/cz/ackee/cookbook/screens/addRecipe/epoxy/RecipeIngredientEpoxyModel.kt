package cz.ackee.cookbook.screens.addRecipe.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.EditText
import com.airbnb.epoxy.EpoxyAttribute
import cz.ackee.cookbook.utils.medium
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.*

/**
 * layout for text of invividual item in list of recipe ingredients
 */
open class RecipeIngredientEpoxyModel : EpoxyModelWithLayout<RecipeIngredientEpoxyLayout>() {

    @EpoxyAttribute
    lateinit var ingredientTitle: String

    override fun createViewLayout(parent: ViewGroup): RecipeIngredientEpoxyLayout = RecipeIngredientEpoxyLayout(parent)

    override fun RecipeIngredientEpoxyLayout.bind() {
        ingredientText.setText(ingredientTitle)
    }
}

class RecipeIngredientEpoxyLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var ingredientText: EditText

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            editText {
                layoutParams = LayoutParams(matchParent, wrapContent)
                isEnabled = false
                isFocusable = false
                typeface = medium
                padding = dip(16)
            }.also { ingredientText = it }
        }
    }
}
