package cz.ackee.skeleton.utils

import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import android.view.ViewGroup
import android.widget.TextView
import cz.ackee.skeleton.R
import org.jetbrains.anko.AnkoContext

/**
 * Extensions for view related classes
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