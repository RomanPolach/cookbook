package cz.ackee.cookbook.model.api

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for recpies
 */
@Entity
data class Recipe(
    @PrimaryKey
    val id: String,
    val name: String,
    val duration: String,
    val score: Float,
    var description: String?,
    var ingredients: List<String>?
)

data class RecipeDetail(
    @Embedded val recipe: Recipe,
    val rated: Boolean?
)

@Entity(tableName = "rated_recipes")
data class RatedRecipes(
    @PrimaryKey
    val recipeId: String,
    val rated: Boolean?)

