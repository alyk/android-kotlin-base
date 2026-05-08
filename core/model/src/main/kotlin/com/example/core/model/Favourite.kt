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

/**
 * Represents a device-local favourite game (no user authentication required).
 * Used for the device-local favourites feature.
 */
@Serializable
data class LocalFavourite(
    val id: Long = 0,
    val gameId: Long,
    val title: String,
    val thumbnailUrl: String,
    val genre: String,
    val platform: String,
    val rating: Float,
    val savedAt: Long = System.currentTimeMillis()
)

/**
 * Represents a device-local favourite with full game details.
 * Used for displaying device-local favourites with complete game information.
 */
@Serializable
data class LocalFavouriteWithGame(
    val favourite: LocalFavourite,
    val game: Game
)