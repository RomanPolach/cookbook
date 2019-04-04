package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.willy.ratingbar.ScaleRatingBar
import cz.ackee.ankoconstraintlayout.constraintLayout
import cz.ackee.cookbook.R
import cz.ackee.extensions.android.color
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Layout for RecipeDetailFragment
 */
class RecipeDetailLayout(context: Context) : ViewLayout(context) {

    lateinit var txtRecipeTitle: TextView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            coordinatorLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

                appBarLayout {

                    collapsingToolbarLayout {
                        fitsSystemWindows = true
                        setContentScrimColor(color(R.color.primary))

                        constraintLayout {
                            fitsSystemWindows = true
                        }.lparams(matchParent, wrapContent) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                            parallaxMultiplier = 0.7f

                            imageView(R.drawable.img_logo_big) {
                                scaleType = ImageView.ScaleType.FIT_XY
                            }.lparams(matchParent, wrapContent)

                            view {
                                alpha = 0.4f
                                backgroundColor = color(R.color.hockeyapp_text_black)
                            }.lparams(matchParent, dip(0))

                            view {
                                backgroundColor = color(R.color.button_pink)
                            }.lparams(matchParent, dip(62))

                            txtRecipeTitle = textView {
                                marginStart = dip(30)
                                bottomMargin = dip(30)
                                textColor = color(R.color.hockeyapp_text_white)
                                textSize = 24f
                            }.lparams(dip(200), wrapContent)

                            ScaleRatingBar
                        }

                    }.lparams(matchParent, matchParent) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }
}