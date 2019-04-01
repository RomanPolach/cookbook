package cz.ackee.skeleton.model.api

/**
 * Sample api entities
 */
data class Recipe(
        val id: String,
        val name: String,
        val duration: Int,
        val score: Float
)