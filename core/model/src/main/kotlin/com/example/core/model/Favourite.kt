package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a favourite game association between a user and a game.
 * This is a cross-reference entity for the favourites feature.
 */
@Serializable
data class Favourite(
    val id: Long,
    val userId: Long,
    val gameId: Long,
    val addedAt: String
)

/**
 * Represents the full favourite with game details included.
 * Used for displaying favourites list with game information.
 */
@Serializable
data class FavouriteWithGame(
    val favourite: Favourite,
    val game: Game
)