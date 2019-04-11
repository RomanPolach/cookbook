package cz.ackee.cookbook.model.api.db

import androidx.room.*
import cz.ackee.cookbook.model.api.Recipe
import io.reactivex.Flowable

/**
 * Room Dao for Recipes
 */
@Dao
interface RecipeDao {

    @Update
    fun insertDetail(recipe: Recipe): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllRecipes(recipes: Collection<Recipe>): List<Long>

    @Query("SELECT * FROM recipe WHERE recipe.id =:recipeId LIMIT 1")
    fun getRecipeById(recipeId: String): Flowable<Recipe>

    @Query("SELECT * FROM Recipe")
    fun getRecipes(): Flowable<List<Recipe>>

    @Query("SELECT * FROM Recipe LIMIT :perPage OFFSET :offset")
    fun getRecipesPaged(perPage: Int, offset: Int): Flowable<List<Recipe>>
}