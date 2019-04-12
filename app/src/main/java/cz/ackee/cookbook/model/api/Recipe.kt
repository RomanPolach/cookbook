package cz.ackee.cookbook.model.api

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Sample api entities
 */
@Entity
data class Recipe(
    @PrimaryKey
    val id: String,
    val name: String,
    val duration: Int,
    val score: Float,
    var description: String?,
    var ingredients: List<String?>?,
    var rated: Boolean?
)