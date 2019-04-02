package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import cz.ackee.extensions.android.attrDimen
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.matchParent

/**
 * UI component of toolbar
 */
class ToolbarLayout(parent: ViewGroup) : ViewLayout(parent) {

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            themedToolbar(theme = cz.ackee.cookbook.R.style.ToolbarTheme) {
                backgroundResource = cz.ackee.cookbook.R.color.primary
                id = cz.ackee.cookbook.R.id.toolbar
                layoutParams = ViewGroup.LayoutParams(matchParent, ctx.attrDimen(cz.ackee.cookbook.R.attr.actionBarSize))
            }
        }
    }
}