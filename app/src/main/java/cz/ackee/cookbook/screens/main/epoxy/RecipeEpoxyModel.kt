package cz.ackee.cookbook.screens.main.epoxy

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import cz.ackee.ankoconstraintlayout.constraintLayout
import cz.ackee.cookbook.R
import cz.ackee.cookbook.utils.titleTextView
import cz.ackee.extensions.android.color
import cz.ackee.extensions.android.drawable
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.epoxy.EpoxyModelWithLayout
import org.jetbrains.anko.*

/**
 * EpoxyModel for showing recipe records
 */
open class RecipeEpoxyModel : EpoxyModelWithLayout<RecipeLayout>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var subtitle: String

    @EpoxyAttribute
    lateinit var time: String

    override fun createViewLayout(parent: ViewGroup) = RecipeLayout(parent)

    override fun RecipeLayout.bind() {
        titleText.text = title
        timeText.text = time
        //   subtitleText.text = subtitle
    }
}

class RecipeLayout(parent: ViewGroup) : ViewLayout(parent) {

    lateinit var titleText: TextView
    lateinit var image: ImageView
    lateinit var myRatingBar: RatingBar
    lateinit var clockImage: ImageView
    lateinit var timeText: TextView
    lateinit var divider: View

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            constraintLayout {
                id = R.id.constraint_layout
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                verticalPadding = dip(8)

                titleText = titleTextView().lparams(width = wrapContent)

                image = imageView {
                    setImageResource(R.drawable.img_small)
                }
                myRatingBar = ratingBar() {
                    progressDrawable = drawable(R.drawable.ratingbar_selector)
                    numStars = 5
                    progress = 3
                }.lparams(width = wrapContent, height = wrapContent)

                clockImage = imageView {
                    setImageResource(R.drawable.ic_time)
                }.lparams(width = wrapContent, height = wrapContent)

                timeText = textView {

                }

                divider = view {
                    backgroundColor = color(R.color.material_grey_100)
                }.lparams(width = matchParent, height = dip(3))

                constraints {
                    image.connect(
                        LEFTS of parentId
                    )

                    titleText.connect(
                        TOPS of parentId,
                        LEFT to RIGHT of image with dip(16)
                    )

                    myRatingBar.connect(
                        TOP to BOTTOM of titleText,
                        LEFT to RIGHT of image with dip(16)
                    )
                    clockImage.connect(
                        TOP to BOTTOM of myRatingBar,
                        LEFTS of myRatingBar
                    )

                    timeText.connect(
                        LEFT to RIGHT of clockImage with dip(16),
                        TOP to BOTTOM of myRatingBar,
                        BOTTOM to BOTTOM of clockImage
                    )

                    divider.connect(
                        TOP to BOTTOM of clockImage with dip(20)
                    )
                }
            }
        }
    }
}