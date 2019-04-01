package cz.ackee.skeleton.model.validation

import android.content.Context
import androidx.annotation.StringRes
import cz.ackee.extensions.android.string

/**
 * Common class for different representations of validation errors.
 */
sealed class ValidationError {

    data class Text(val text: String) : ValidationError() {
        override fun format(context: Context) = text
    }

    data class Resource(@StringRes val resId: Int) : ValidationError() {
        override fun format(context: Context) = context.string(resId)
    }

    abstract fun format(context: Context): String
}