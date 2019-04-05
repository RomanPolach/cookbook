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
import cz.ackee.cookbook.utils.titleTextView
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.drawableLeft
import cz.ackee.extensions.anko.layout.ViewLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Layout for RecipeDetailFragment
 */
class RecipeDetailLayout(context: Context) : ViewLayout(context) {

    lateinit var txtRecipeTitle: TextView
    lateinit var scoreRatingBar: ScaleRatingBar
    lateinit var txtTime: TextView
    lateinit var txtRecipeDescription: TextView
    lateinit var txtRecipeIntro: TextView
    lateinit var scoreBottomRatingBar: ScaleRatingBar

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            coordinatorLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

                appBarLayout {

                    collapsingToolbarLayout {
                        fitsSystemWindows = true
                        setContentScrimColor(color(R.color.primary))

//                        toolbar().lparams {
//                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
//                            parallaxMultiplier = 0.7f
//                        }

                        constraintLayout {
                            fitsSystemWindows = true

                            val imgLogo = imageView(R.drawable.img_logo_big) {
                                scaleType = ImageView.ScaleType.FIT_XY
                            }.lparams(matchParent, wrapContent)

                            val viewShadow = view {
                                alpha = 0.4f
                                backgroundColor = color(R.color.hockeyapp_text_black)
                            }.lparams(matchParent, dip(0))

                            val viewPink = view {
                                backgroundColor = color(R.color.button_pink)
                            }.lparams(matchParent, dip(62))

                            txtRecipeTitle = textView {
                                textColor = color(R.color.hockeyapp_text_white)
                                textSize = 24f
                                bottomPadding = dip(20)
                                leftPadding = dip(20)
                            }.lparams(dip(200), wrapContent)

                            scoreRatingBar = customView {
                                setIsIndicator(true)
                                setNumStars(5)
                                starPadding = dip(3)
                                stepSize = 0.5f
                                leftPadding = dip(20)
                                setEmptyDrawableRes(R.drawable.ic_star)
                                setFilledDrawableRes(R.drawable.ic_star_white)
                            }

                            txtTime = textView {
                                rightPadding = dip(20)
                                drawableLeft = R.drawable.ic_time
                                compoundDrawablePadding = dip(10)
                            }

                            constraints {
                                imgLogo.connect(
                                    TOPS of parentId,
                                    LEFTS of parentId
                                )

                                viewShadow.connect(
                                    TOPS of parentId,
                                    BOTTOMS of imgLogo
                                )

                                txtRecipeTitle.connect(
                                    BOTTOM to TOP of viewPink,
                                    LEFTS of parentId
                                )

                                viewPink.connect(
                                    BOTTOMS of imgLogo,
                                    LEFTS of parentId
                                )

                                txtTime.connect(
                                    BOTTOM to BOTTOM of viewPink,
                                    TOP to TOP of viewPink,
                                    RIGHTS of parentId
                                )

                                scoreRatingBar.connect(
                                    BOTTOM to BOTTOM of viewPink,
                                    TOP to TOP of viewPink,
                                    LEFTS of parentId
                                )
                            }
                        }.lparams(matchParent, wrapContent) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                            parallaxMultiplier = 0.7f
                        }
                    }.lparams(matchParent, matchParent) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                    }
                }.lparams(width = matchParent, height = wrapContent)

                nestedScrollView {
                    isFillViewport = true

                    verticalLayout {
                        txtRecipeIntro = textView {
                            padding = dip(20)
                        }

                        titleTextView(R.string.add_recipe_ingredients_title) {
                            padding = dip(20)
                        }

                        titleTextView(R.string.recipe_detail_food_preparation_title) {
                            padding = dip(20)
                        }

                        txtRecipeDescription = textView {
                            padding = dip(20)
                        }

//                        val viewBottomRating = view {
//                            backgroundColor = color(R.color.title_text)
//                        }.lparams(matchParent, dip(200))

                        val txtRateThisRecipe = textView(R.string.recipe_detail_rate_this_title) {
                            padding = dip(20)
                        }

                        scoreBottomRatingBar = customView {
                            textView(R.string.recipe_detail_rate_this_title).lparams {
                                bottomMargin = dip(-50)
                            }
                            setNumStars(5)
                            backgroundColor = color(R.color.title_text)
                            starPadding = dip(3)
                            stepSize = 0.5f
                            topPadding = dip(100)
                            bottomPadding = dip(50)
                            leftPadding = dip(20)
                            setEmptyDrawableRes(R.drawable.ic_star_trans_big)
                            setFilledDrawableRes(R.drawable.ic_star_white_big)
                        }
                    }.lparams(matchParent, wrapContent)

                }.lparams(matchParent, matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }
    }
}