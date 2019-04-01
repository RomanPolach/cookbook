package cz.ackee.skeleton.screens.main.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import cz.ackee.skeleton.utils.defaultTextView
import cz.ackee.skeleton.utils.titleTextView
import org.jetbrains.anko.*

/**
 * EpoxyModel for showing recipe records
 */
open class RecipeEpoxyModel : EpoxyModelWithLayout<RecipeLayout>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var subtitle: String

    override fun createViewLayout(parent: ViewGroup) = RecipeLayout(parent)

    override fun RecipeLayout.bind() {
        titleText.text = title
        subtitleText.text = subtitle
    }
}

class RecipeLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var titleText: TextView
    lateinit var subtitleText: TextView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                verticalPadding = dip(8)

                titleText = titleTextView().lparams(width = matchParent)
                subtitleText = defaultTextView().lparams(width = matchParent)
            }
        }
    }
}