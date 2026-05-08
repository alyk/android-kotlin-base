package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a game entity with all its properties.
 */
@Serializable
data class Game(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val thumbnailUrl: String = "",
    val genre: Genre = Genre.OTHER,
    val platform: Platform = Platform.PC,
    val developer: String = "",
    val publisher: String = "",
    val releaseDate: String = "",
    val rating: Float = 0f,
    val price: Double = 0.0,
    val isFree: Boolean = false,
    val isFeatured: Boolean = false,
    val minimumRequirements: String? = null,
    val recommendedRequirements: String? = null,
    val screenshots: List<String> = emptyList()
)

/**
 * Supported game genres.
 */
@Serializable
enum class Genre {
    ACTION,
    ADVENTURE,
    RPG,
    STRATEGY,
    SIMULATION,
    SPORTS,
    PUZZLE,
    INDIE,
    OTHER
}

/**
 * Supported gaming platforms.
 */
@Serializable
enum class Platform {
    PC,
    PLAYSTATION,
    XBOX,
    NINTENDO,
    MOBILE,
    WEB,
    CROSSPLATFORM,
    OTHER
}