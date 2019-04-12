package cz.ackee.cookbook

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cz.ackee.cookbook.model.api.RatedRecipes
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.db.RecipeDao
import cz.ackee.cookbook.model.api.db.RoomStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * TODO add class description
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RoomStore

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomStore::class.java).allowMainThreadQueries().build()
        recipeDao = db.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRecipeAndRead() {
        val recipes = listOf(Recipe(id = "Franta", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null),
            Recipe(id = "Pepa", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null),
            Recipe(id = "Tonda", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null)
        )

        recipeDao.insertAllRecipes(recipes)
        recipeDao.getRecipes().test().awaitCount(1).assertValues(recipes)
    }

    @Test
    @Throws(Exception::class)
    fun testRatedRecipesReturnTrueValues() {
        val recipeVoted = RatedRecipes("Frantovo", true)
        val recipe = Recipe(id = "Frantovo", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null)
        recipeDao.insertRecipeVoted(recipeVoted)
        recipeDao.insertAllRecipes(listOf(recipe))
        val savedRecipe = recipeDao.getRecipeDetail("Frantovo").test().awaitCount(1)
        savedRecipe.assertValue {
            it.rated == true
        }
        savedRecipe.assertValue {
            it.recipe.id == "Frantovo"
        }
        savedRecipe.assertNoErrors()
    }

    @Test
    @Throws(Exception::class)
    fun testNotRatedRecipeReturnsNull() {
        val recipe = Recipe(id = "Frantovo", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null)
        recipeDao.insertAllRecipes(listOf(recipe))
        val savedRecipe = recipeDao.getRecipeDetail("Frantovo").test().awaitCount(1)
        savedRecipe.assertValue {
            it.rated == null
        }
        savedRecipe.assertValue {
            it.recipe.id == "Frantovo"
        }
        savedRecipe.assertNoErrors()
    }
}