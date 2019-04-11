package cz.ackee.cookbook.model.api

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
    val duration: Int,
    val score: Float,
    var description: String?,
    var ingredients: List<String>?
)