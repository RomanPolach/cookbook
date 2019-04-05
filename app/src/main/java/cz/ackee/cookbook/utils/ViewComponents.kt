package cz.ackee.cookbook.utils

import android.view.ViewManager
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.textfield.TextInputLayout
import cz.ackee.cookbook.R
import cz.ackee.extensions.android.color
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.themedTextInputLayout

/**
 * All the styled components used in the whole app
 */

/* Default title TextView builders */

/**
 * Title textView is used for the most important labels
 */
fun ViewManager.titleTextView(init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return textView {
        textSize = 20f
        typeface = bold
        textColor = color(R.color.title_text)
        setLineSpacing(0f, 1.2f)
        init()
    }
}

inline fun ViewManager.epoxyRecyclerView(init: (@AnkoViewDslMarker EpoxyRecyclerView).() -> Unit = {}): EpoxyRecyclerView {
    return ankoView({ context -> EpoxyRecyclerView(context) }, theme = 0) { init() }
}

fun ViewManager.titleTextView(text: Int, init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return titleTextView {
        init()
        textResource = text
    }
}

fun ViewManager.titleTextView(text: CharSequence?, init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return titleTextView {
        init()
        this.text = text
    }
}

/* Default TextView builders */

/**
 * Default textView is used for most of the texts
 */
fun ViewManager.defaultTextView(init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return textView {
        textSize = 16f
        typeface = regular
        textColor = color(R.color.default_text)
        setLineSpacing(0f, 1.2f)
        init()
    }
}

fun ViewManager.defaultTextView(text: Int, init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return defaultTextView {
        init()
        setText(text)
    }
}

fun ViewManager.defaultTextView(text: CharSequence?, init: (@AnkoViewDslMarker TextView).() -> Unit = {}): TextView {
    return defaultTextView {
        init()
        setText(text)
    }
}

/* Primary button builders */

fun ViewManager.primaryButton(init: Button.() -> Unit = {}): Button {
    return button {
        textColor = color(R.color.primary_button_text)
        typeface = medium
        textSize = 16f
        backgroundColor = color(R.color.primary_button_background)
        init()
    }
}

fun ViewManager.primaryButton(text: Int, init: Button.() -> Unit = {}): Button {
    return primaryButton {
        init()
        textResource = text
    }
}

fun ViewManager.primaryButton(text: CharSequence, init: Button.() -> Unit = {}): Button {
    return primaryButton {
        init()
        this.text = text
    }
}

/* Secondary button builders */

fun ViewManager.secondaryButton(init: Button.() -> Unit = {}): Button {
    return button {
        textColor = color(R.color.primary_button_text)
        typeface = medium
        textSize = 16f
        backgroundColor = color(R.color.secondary_button_background)
        this.text = text
        init()
    }
}

fun ViewManager.secondaryButton(text: Int, init: Button.() -> Unit = {}): Button {
    return secondaryButton {
        init()
        textResource = text
    }
}

fun ViewManager.secondaryButton(text: CharSequence, init: Button.() -> Unit = {}): Button {
    return secondaryButton {
        init()
        this.text = text
    }
}

fun ViewManager.defaultTextInputLayout(init: (@AnkoViewDslMarker TextInputLayout).() -> Unit = {}): TextInputLayout {
    return themedTextInputLayout(R.style.TextInputLayoutAppearance) {
        setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_HintText)

        textInputEditText {
            typeface = medium
            padding = dip(16)
        }
        init()
    }
}
