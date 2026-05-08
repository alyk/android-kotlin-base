package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a game entity in the domain model.
 * This is the core domain entity used across all feature modules.
 */
@Serializable
data class Game(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val genre: Genre,
    val platform: Platform,
    val releaseDate: String,
    val rating: Float,
    val developer: String,
    val publisher: String,
    val isFeatured: Boolean = false
)

/**
 * Represents the genre/category of a game
 */
@Serializable
enum class Genre(val displayName: String) {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    RPG("RPG"),
    STRATEGY("Strategy"),
    SIMULATION("Simulation"),
    SPORTS("Sports"),
    PUZZLE("Puzzle"),
    HORROR("Horror"),
    INDIE("Indie"),
    OTHER("Other")
}

/**
 * Represents the gaming platform
 */
@Serializable
enum class Platform(val displayName: String) {
    PC("PC"),
    PLAYSTATION("PlayStation"),
    XBOX("Xbox"),
    NINTENDO("Nintendo"),
    MOBILE("Mobile"),
    CROSSPLATFORM("Cross-Platform")
}