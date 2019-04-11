package cz.ackee.cookbook.model.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.ackee.cookbook.model.api.RatedRecipes
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.converters.Converters

/**
 * Room DB initialisation
 */
@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Recipe::class,
        RatedRecipes::class
    ]
)
@TypeConverters(Converters::class)
abstract class RoomStore : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}