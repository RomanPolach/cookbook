package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import cz.ackee.ankoconstraintlayout.constraintLayout
import cz.ackee.cookbook.R
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.AnkoContext

/**
 * UI component of toolbar
 */
class AddRecipeLayout(parent: ViewGroup) : ViewLayout(parent) {

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                layoutParams = ViewGroup.LayoutParams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                id = R.id.constraint_layout

            }
        }
    }
}