package cz.ackee.cookbook.screens.main.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.willy.ratingbar.ScaleRatingBar
import cz.ackee.ankoconstraintlayout.constraintLayout
import cz.ackee.cookbook.R
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.utils.titleTextView
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.drawableLeft
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView

/**
 * EpoxyModel for showing recipe records
 */
open class RecipeEpoxyModel : EpoxyModelWithLayout<RecipeLayout>() {

    @EpoxyAttribute
    lateinit var recipeItem: Recipe

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onRecipeClick: (id: String) -> Unit

    override fun createViewLayout(parent: ViewGroup) = RecipeLayout(parent)

    override fun RecipeLayout.bind() {
        txtTitle.text = recipeItem.name
        txtTime.text = "${recipeItem.duration} ${view.context.getString(R.string.main_fragment_minutes)}"
        //round to 0.5
        scoreRatingBar.rating = recipeItem.score
        view.setOnClickListener {
            onRecipeClick(recipeItem.id)
        }
    }
}

class RecipeLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var txtTitle: TextView
    lateinit var scoreRatingBar: ScaleRatingBar
    lateinit var txtTime: TextView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                id = R.id.constraint_layout
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                verticalPadding = dip(8)

                txtTitle = titleTextView {
                    topPadding = dip(25)
                }.lparams(width = wrapContent)

                val imgLogo = imageView(R.drawable.img_logo_small) {
                    verticalPadding = dip(20)
                }

                scoreRatingBar = customView {
                    setIsIndicator(true)
                    setNumStars(5)
                    starPadding = dip(0)
                    stepSize = 0.5f
                    setEmptyDrawableRes(R.drawable.ic_star_white)
                    setFilledDrawableRes(R.drawable.ic_star)
                }

                txtTime = textView {
                    textColor = color(R.color.hockeyapp_text_black)
                    drawableLeft = R.drawable.ic_time
                    compoundDrawablePadding = dip(10)
                }

                constraints {
                    imgLogo.connect(
                        STARTS of parentId,
                        TOPS of parentId
                    )

                    txtTitle.connect(
                        TOPS of imgLogo,
                        START to END of imgLogo with dip(16)
                    )

                    scoreRatingBar.connect(
                        TOP to BOTTOM of txtTitle with dip(5),
                        START to END of imgLogo with dip(16)
                    )

                    txtTime.connect(
                        START to END of imgLogo with dip(16),
                        BOTTOM to BOTTOM of imgLogo with dip(25)
                    )
                }
            }
        }
    }
}
