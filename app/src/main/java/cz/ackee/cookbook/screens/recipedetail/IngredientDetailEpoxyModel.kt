package cz.ackee.cookbook.screens.recipedetail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import cz.ackee.cookbook.R
import cz.ackee.extensions.android.drawableLeft
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalPadding

/**
 * EpoxyModel for ingredients list in Recipe Detail
 */
open class IngredientDetailEpoxyModel : EpoxyModelWithLayout<IngredientDetailEpoxyLayout>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun createViewLayout(parent: ViewGroup) = IngredientDetailEpoxyLayout(parent)

    override fun IngredientDetailEpoxyLayout.bind() {
        titleText.text = title
    }
}

class IngredientDetailEpoxyLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var titleText: TextView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            textView {
                drawableLeft = R.drawable.dot_shape
                compoundDrawablePadding = dip(20)
                verticalPadding = dip(20)
            }.also { titleText = it }
        }
    }
}


