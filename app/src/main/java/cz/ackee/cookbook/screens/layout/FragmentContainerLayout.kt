package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.cookbook.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

/**
 * Component for Fragment container
 */
class FragmentContainerLayout(parent: ViewGroup) : ViewLayout(parent) {

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            frameLayout {
                id = R.id.fragment_container
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
            }
        }
    }
}