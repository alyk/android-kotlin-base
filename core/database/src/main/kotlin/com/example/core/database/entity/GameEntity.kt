package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform

/**
 * Room entity representing a game in the local database.
 * Maps to the 'games' table.
 */
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val genre: String,
    val platform: String,
    val releaseDate: String,
    val rating: Float,
    val developer: String,
    val publisher: String,
    val isFeatured: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
) {
    /**
     * Converts entity to domain model
     */
    fun toDomain(): Game {
        return Game(
            id = id,
            title = title,
            description = description,
            thumbnailUrl = thumbnailUrl,
            genre = try {
                Genre.valueOf(genre)
            } catch (e: IllegalArgumentException) {
                Genre.OTHER
            },
            platform = try {
                Platform.valueOf(platform)
            } catch (e: IllegalArgumentException) {
                Platform.CROSSPLATFORM
            },
            releaseDate = releaseDate,
            rating = rating.coerceIn(0f, 5f),
            developer = developer,
            publisher = publisher,
            isFeatured = isFeatured
        )
    }
    
    companion object {
        /**
         * Creates entity from domain model
         */
        fun fromDomain(game: Game): GameEntity {
            return GameEntity(
                id = game.id,
                title = game.title,
                description = game.description,
                thumbnailUrl = game.thumbnailUrl,
                genre = game.genre.name,
                platform = game.platform.name,
                releaseDate = game.releaseDate,
                rating = game.rating,
                developer = game.developer,
                publisher = game.publisher,
                isFeatured = game.isFeatured
            )
        }
    }
}

/**
 * Room entity for game detail information.
 * Provides additional cached information about games.
 */
@Entity(tableName = "game_details")
data class GameDetailEntity(
    @PrimaryKey
    val gameId: Long,
    val screenshots: String, // JSON serialized list
    val videos: String, // JSON serialized list
    val os: String?,
    val processor: String?,
    val memory: String?,
    val graphics: String?,
    val storage: String?,
    val tags: String, // JSON serialized list
    val languages: String, // JSON serialized list
    val price: Double?,
    val websiteUrl: String?,
    val cachedAt: Long = System.currentTimeMillis()
)

/**
 * Room entity for caching search results.
 * Helps implement offline search functionality.
 */
@Entity(tableName = "search_cache")
data class SearchCacheEntity(
    @PrimaryKey
    val query: String,
    val genres: String,
    val platforms: String,
    val resultsJson: String, // JSON serialized list of game IDs
    val totalCount: Int,
    val cachedAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = cachedAt + CACHE_DURATION_MS
) {
    companion object {
        const val CACHE_DURATION_MS = 24 * 60 * 60 * 1000L // 24 hours
    }
}