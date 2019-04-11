package cz.ackee.cookbook.model.api.db

import androidx.room.*
import cz.ackee.cookbook.model.api.RatedRecipes
import cz.ackee.cookbook.model.api.Recipe
import cz.ackee.cookbook.model.api.RecipeDetail
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipeVoted(recipe: RatedRecipes)

    @Query("SELECT * from RECIPE LEFT OUTER JOIN rated_recipes on (recipe.id = rated_recipes.recipeId) where recipe.id =:id")
    fun getRecipeDetail(id: String): Flowable<RecipeDetail>

    @Query("SELECT * FROM Recipe LIMIT :perPage OFFSET :offset")
    fun getRecipesPaged(perPage: Int, offset: Int): Flowable<List<Recipe>>
}