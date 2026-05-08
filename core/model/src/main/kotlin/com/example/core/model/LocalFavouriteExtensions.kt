package com.example.core.model

/**
 * Extension functions for LocalFavourite conversions.
 */

/**
 * Converts a LocalFavourite to a Game for use with UI components.
 * This allows reuse of GameCard component from core:ui.
 */
fun LocalFavourite.toGame(): Game {
    return Game(
        id = gameId,
        title = title,
        description = "",
        thumbnailUrl = thumbnailUrl,
        genre = genreFromString(genre),
        platform = platformFromString(platform),
        developer = "", // LocalFavourite doesn't store developer
        publisher = "",
        releaseDate = "",
        rating = rating,
        price = 0.0,
        isFree = false,
        isFeatured = false
    )
}

/**
 * Converts a string genre to Genre enum.
 */
private fun genreFromString(genre: String): Genre {
    return when (genre.uppercase()) {
        "ACTION" -> Genre.ACTION
        "ADVENTURE" -> Genre.ADVENTURE
        "RPG" -> Genre.RPG
        "STRATEGY" -> Genre.STRATEGY
        "SIMULATION" -> Genre.SIMULATION
        "SPORTS" -> Genre.SPORTS
        "PUZZLE" -> Genre.PUZZLE
        "INDIE" -> Genre.INDIE
        else -> Genre.OTHER
    }
}

/**
 * Converts a string platform to Platform enum.
 */
private fun platformFromString(platform: String): Platform {
    return when (platform.uppercase()) {
        "PC" -> Platform.PC
        "PLAYSTATION" -> Platform.PLAYSTATION
        "XBOX" -> Platform.XBOX
        "NINTENDO" -> Platform.NINTENDO
        "MOBILE" -> Platform.MOBILE
        "WEB" -> Platform.WEB
        "CROSSPLATFORM" -> Platform.CROSSPLATFORM
        else -> Platform.OTHER
    }
}