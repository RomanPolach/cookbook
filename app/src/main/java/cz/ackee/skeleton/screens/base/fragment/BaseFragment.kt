package cz.ackee.skeleton.screens.base.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.ackee.extensions.android.hideIme
import cz.ackee.extensions.anko.layout.ViewLayout
import cz.ackee.skeleton.R
import cz.ackee.skeleton.screens.base.activity.FragmentActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Fragment from which all applications fragment should inherit
 */
abstract class BaseFragment<T : ViewLayout> : Fragment() {

    val fragmentActivity: FragmentActivity?
        get() = activity as? FragmentActivity

    /**
     * Indicator if this fragment has its own toolbar and does not use toolbar in activity. Useful
     * when multiple fragments with different kinds of toolbars are used in the same activity
     */
    open var provideToolbar: Boolean = false
    protected var disposables = CompositeDisposable()

    protected val toolbar: Toolbar?
        get() {
            // if fragment is providing its own toolbar, return this one otherwise toolbar from activity
            if (provideToolbar) {
                return providedToolbar
            }
            return fragmentActivity?.toolbar
        }

    open val providedToolbar: Toolbar?
        get() {
            return view?.findViewById(R.id.toolbar)
        }

    protected lateinit var layout: T

    init {
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    abstract fun createLayout(parent: Context): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return createLayout(container!!.context).also { layout = it }.view
    }

    open fun T.viewCreated(savedState: Bundle?) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout.viewCreated(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        if (provideToolbar) {
            fragmentActivity?.setSupportActionBar(toolbar)
        }
        onInitActionBar(fragmentActivity?.supportActionBar, toolbar)
    }

    /**
     * Method that is called when actionbar (toolbar) should be initialized for this fragment
     *
     * @param actionBar of activity
     * @param toolbar   representation of ActionBar
     */
    open fun onInitActionBar(actionBar: ActionBar?, toolbar: Toolbar?) {
    }

    /**
     * Basic settings of actionbar. You should modify this with project specific settings
     */
    protected fun baseActionBarSettings(actionBar: ActionBar?) {
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        setTitle(getTitle())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideIme()
        disposables.clear()
    }

    /**
     * Indicator if fragment handles [android.app.Activity.onBackPressed] call from activity. Useful for form fragments when
     * you want to show user warning dialog when back button is pressed
     */
    fun overrideOnBackPressed(): Boolean {
        return false
    }

    /**
     * Hides keyboard if it is shown
     */
    protected fun hideIme() {
        // try to hide keyboard when destroying fragment view
        view?.findFocus()?.hideIme()
    }

    /**
     * Set title of toolbar
     */
    protected fun setTitle(@StringRes title: Int) {
        setTitle(getString(title))
    }

    /**
     * Set title of toolbar
     */
    protected fun setTitle(title: String?) {
        // intentional not-safe way to call activity, we want to crash an app when activity is null because
        // we are probably doing something bad
        activity!!.title = title
    }

    /**
     * Get title of screen. Children should override this if they want custom title
     */
    open fun getTitle(): String {
        return ""
    }
}
