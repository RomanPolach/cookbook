package cz.ackee.cookbook.model.api

/**
 * Sample api entities
 */
data class Recipe(
    val id: String,
    val name: String,
    val duration: Int,
    val score: Float,
    var description: String?,
    var ingredients: List<String?>?
)