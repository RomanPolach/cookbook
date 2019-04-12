package cz.ackee.cookbook

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
        recipeDao.insertAllRecipes(
            listOf(Recipe(id = "Franta", name = "Takz Franta", description = "nevim", duration = 5, score = 4f, ingredients = null)))
        recipeDao.getRecipes().test().awaitCount(1).assertValue {
            it.get(0).id == "Frant"
        }
    }
}