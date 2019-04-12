package cz.ackee.cookbook.screens.layout

import android.content.Context
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import cz.ackee.cookbook.R
import cz.ackee.extensions.android.visible
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.extensions.recyclerview.onItemClick
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Component with epoxyRecyclerView, progress and empty view
 *
 * @property parent ViewGroup that is parent of this layout
 * @property controller EpoxyController that will be used to build models
 * @property manager Layout manager, epoxyRecyclerView uses [LinearLayoutManager] by default since we set height as matchParent.
 * @property itemDecoration item decorations eg. dividers, default null
 * @property emptyTextId resource id of empty text, default null
 * @property errorLayout layout for error view, default null
 * @property onScrolledToEnd callback when epoxyRecyclerView is scrolled to the end, default null
 * @property onItemClick click listener to items, default null
 **/
class ListLayout(context: Context,
    val controller: EpoxyController? = null,
    val manager: RecyclerView.LayoutManager? = null,
    val itemDecoration: RecyclerView.ItemDecoration? = null,
    @StringRes val emptyTextId: Int? = null,
    val emptyLayout: ViewLayout? = null,
    val errorLayout: ViewLayout? = null,
    val onScrolledToEnd: (() -> Unit)? = null,
    val onSwipeRefresh: (() -> Unit)? = null,
    val onItemClick: ((position: Int) -> Unit)? = null
) : ViewLayout(context) {

    lateinit var content: View
    lateinit var epoxyRecyclerView: EpoxyRecyclerView
    lateinit var progress: ProgressBar
    lateinit var empty: View
    lateinit var error: View
    lateinit var scrollListener: InfiniteScrollListener
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            frameLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

                content = frameLayout {
                    swipeRefreshLayout = swipeRefreshLayout {
                        isEnabled = onSwipeRefresh != null
                        setOnRefreshListener(onSwipeRefresh)

                        epoxyRecyclerView = EpoxyRecyclerView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

                            if (manager != null) {
                                layoutManager = manager
                            }

                            if (controller != null) {
                                setController(controller)
                            }

                            if (onItemClick != null) {
                                onItemClick(onItemClick)
                            }

                            if (itemDecoration != null) {
                                addItemDecoration(itemDecoration)
                            }
                            if (onScrolledToEnd != null) {
                                with(layoutManager) {
                                    if (this !is LinearLayoutManager) {
                                        throw IllegalStateException("Built-in scrolling is only available for LinearLayoutManager")
                                    }
                                    scrollListener = InfiniteScrollListener(onScrolledToEnd, this)
                                    addOnScrollListener(scrollListener)
                                }
                            }
                        }

                        addView(epoxyRecyclerView)
                    }.lparams(width = matchParent, height = matchParent)

                    empty = frameLayout {
                        visible = false

                        if (emptyTextId != null) {
                            textView {
                                textResource = emptyTextId
                                gravity = CENTER
                                padding = dimen(R.dimen.medium_space)
                            }.lparams(gravity = CENTER)
                        } else if (emptyLayout != null) {
                            addView(emptyLayout.view.lparams(gravity = CENTER))
                        }
                    }

                    error = frameLayout {
                        visible = false

                        if (errorLayout != null) {
                            addView(errorLayout.view.lparams(gravity = CENTER))
                        }
                    }
                }.lparams(width = matchParent, height = matchParent)

                progress = progressBar {
                    visible = false
                }.lparams {
                    gravity = CENTER
                }
            }
        }
    }

    /**
     * Show progress view based on [show] param
     */
    fun showProgress(show: Boolean) {
        progress.visible = show
        content.visible = !show
    }

    /**
     * Show or hide the empty placeholder
     */
    fun showEmpty(show: Boolean) {
        empty.visible = show
    }

    /**
     * Show or hide the error banner
     */
    fun showError(show: Boolean) {
        error.visible = show
    }

    /**
     * Set if the infinite scroll listener should continue loading
     */
    fun setContinueLoading(continueLoading: Boolean) {
        scrollListener.continueLoading = continueLoading
    }
}

class InfiniteScrollListener(val func: () -> Unit,
    val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    var continueLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (totalItemCount < 4) {
            return  // workaround, func is called before the data is set and call listener method
        }

        if (continueLoading) {
            if (visibleItemCount + firstVisibleItem >= totalItemCount) {
                func()
                continueLoading = false
            }
        }
    }
}