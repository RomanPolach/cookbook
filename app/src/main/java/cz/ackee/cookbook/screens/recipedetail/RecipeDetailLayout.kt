package cz.ackee.cookbook.screens.recipedetail

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import cz.ackee.cookbook.R
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.button

/**
 * Layout for RecipeDetailFragment
 */
class RecipeDetailLayout(context: Context) : ViewLayout(context) {
    var setik: ConstraintSet? = null
    var setik2: ConstraintSet? = null

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            MotionLayout(context).apply {
                id = R.id.franta_layout

                button("franta") {
                    id = R.id.button_moving
                }

                setik = ConstraintSet()
                setik.apply {
                    id = R.id.button_start
                }
                setik!!.setForceId(true)
                setik!!.connect(R.id.button_moving, TOP, R.id.franta_layout, TOP)

                setik2 = ConstraintSet()
                setik2!!.setForceId(true)
                setik2.apply {
                    id = R.id.button_end
                }
                setik2!!.connect(R.id.button_moving, BOTTOM, R.id.franta_layout, BOTTOM)

                setScene(MotionScene(this))
                setTransition(R.id.button_start, R.id.button_end)
                setTransitionDuration(1000)
            }
        }
    }
}