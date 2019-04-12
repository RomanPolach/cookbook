package cz.ackee.cookbook.screens.main.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelClass
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.*

/**
 * Epoxy Progress bar item
 */
@EpoxyModelClass
open class ProgressModel : EpoxyModelWithLayout<ProgressLayout>() {

    override fun createViewLayout(parent: ViewGroup) = ProgressLayout(parent)

    override fun ProgressLayout.bind() {
    }
}

class ProgressLayout(parent: ViewGroup) : ViewLayout(parent) {

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            frameLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)

                progressBar {
                }
            }
        }
    }
}