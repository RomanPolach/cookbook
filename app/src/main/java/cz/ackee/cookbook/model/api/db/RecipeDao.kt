package cz.ackee.cookbook.model.api.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.ackee.cookbook.model.api.Recipe
import io.reactivex.Flowable

/**
 * Room Dao for Recipes
 */
@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipes(recipes: Collection<Recipe>): List<Long>

    @Query("SELECT * FROM recipe WHERE recipe.id =:recipeId LIMIT 1")
    fun getRecipeById(recipeId: String): LiveData<Recipe>

    @Query("SELECT * FROM Recipe")
    fun getRecipes(): Flowable<List<Recipe>>

    @Query("UPDATE recipe SET rated=:voted WHERE id = :id")
    fun setUserVoted(id: String, voted: Boolean)
}