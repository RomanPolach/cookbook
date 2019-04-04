package cz.ackee.cookbook.screens.main

import android.os.Bundle
import cz.ackee.cookbook.R
import cz.ackee.cookbook.screens.addRecipe.RecipeDetailFragment
import cz.ackee.cookbook.screens.base.activity.FragmentActivity

/**
 * Main activity of application
 */
class MainActivity : FragmentActivity() {

    override val fragmentName: String? = RecipeDetailFragment::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
}