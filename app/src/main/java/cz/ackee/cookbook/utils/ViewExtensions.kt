package cz.ackee.cookbook.utils

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import cz.ackee.cookbook.R
import org.jetbrains.anko.AnkoContext

/**
 * Extensions for view related classes
 * David Bilík je hloupej
 */

/**
 * Get Anko context from view group
 */
val ViewGroup.ankoContext
    get() = AnkoContext.Companion.create(context, this)

val TextView.regular: Typeface
    get() = ResourcesCompat.getFont(context, R.font.roboto_regular)!!

val TextView.bold: Typeface
    get() = ResourcesCompat.getFont(context, R.font.roboto_bold)!!

val TextView.medium: Typeface
    get() = ResourcesCompat.getFont(context, R.font.roboto_medium)!!

/** Easily add models to an EpoxyRecyclerView, the same way you would in a buildModels method of EpoxyController. */
fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            buildModelsCallback()
        }
    })
}