package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a game that a user has marked as favourite.
 * Links a User entity with a Game entity through a many-to-many relationship.
 */
@Serializable
data class UserGame(
    val id: Long = 0,
    val game: Game,
    val userId: String = "default_user",
    val addedAt: Long? = System.currentTimeMillis()
)