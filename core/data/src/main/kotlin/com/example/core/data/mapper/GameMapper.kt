package com.example.core.data.mapper

import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Genre
import com.example.core.model.Platform

/**
 * Data transformation utilities for converting between API DTOs and domain models.
 * This file provides extension functions and mapping utilities.
 */

/**
 * Extension function to convert API genre string to Genre enum
 */
fun String.toGenre(): Genre {
    return try {
        Genre.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        Genre.OTHER
    }
}

/**
 * Extension function to convert Genre enum to API string
 */
fun Genre.toApiString(): String {
    return this.name.lowercase()
}

/**
 * Extension function to convert API platform string to Platform enum
 */
fun String.toPlatform(): Platform {
    return try {
        Platform.valueOf(this.uppercase().replace(" ", ""))
    } catch (e: IllegalArgumentException) {
        Platform.CROSSPLATFORM
    }
}

/**
 * Extension function to convert Platform enum to API string
 */
fun Platform.toApiString(): String {
    return this.name.lowercase()
}

/**
 * Converts a list of genre strings to Genre enums
 */
fun List<String>.toGenreList(): List<Genre> {
    return this.map { it.toGenre() }
}

/**
 * Converts a list of Genre enums to API strings
 */
fun List<Genre>.toGenreApiList(): String {
    return this.joinToString(",") { it.toApiString() }
}

/**
 * Converts a list of platform strings to Platform enums
 */
fun List<String>.toPlatformList(): List<Platform> {
    return this.map { it.toPlatform() }
}

/**
 * Converts a list of Platform enums to API strings
 */
fun List<Platform>.toPlatformApiList(): String {
    return this.joinToString(",") { it.toApiString() }
}

/**
 * Helper function to format rating to 1 decimal place
 */
fun Float.formatRating(): Float {
    return String.format("%.1f", this).toFloat()
}

/**
 * Helper function to validate and clamp rating between 0 and 5
 */
fun Float.sanitizeRating(): Float {
    return this.coerceIn(0f, 5f)
}

/**
 * Converts release date string to a standardized format
 */
fun String.formatReleaseDate(): String {
    // Assuming input format is "YYYY-MM-DD"
    return try {
        val parts = this.split("-")
        if (parts.size == 3) {
            "${parts[1]}/${parts[2]}/${parts[0]}"
        } else {
            this
        }
    } catch (e: Exception) {
        this
    }
}

/**
 * Data class for API DTOs that don't match domain models exactly.
 * Used for intermediate transformation.
 */
data class GameDto(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val genre: String,
    val platform: String,
    val releaseDate: String,
    val rating: Float,
    val developer: String,
    val publisher: String,
    val isFeatured: Boolean = false
) {
    /**
     * Converts DTO to domain model
     */
    fun toDomain(): Game {
        return Game(
            id = id,
            title = title,
            description = description,
            thumbnailUrl = imageUrl,
            genre = genre.toGenre(),
            platform = platform.toPlatform(),
            releaseDate = releaseDate.formatReleaseDate(),
            rating = rating.sanitizeRating().formatRating(),
            developer = developer,
            publisher = publisher,
            isFeatured = isFeatured
        )
    }
}

/**
 * Extension function to convert list of DTOs to domain models
 */
fun List<GameDto>.toDomainList(): List<Game> {
    return this.map { it.toDomain() }
}

/**
 * Converts a domain Game to a DTO for API calls
 */
fun Game.toDto(): GameDto {
    return GameDto(
        id = id,
        title = title,
        description = description,
        imageUrl = thumbnailUrl,
        genre = genre.toApiString(),
        platform = platform.toApiString(),
        releaseDate = releaseDate,
        rating = rating,
        developer = developer,
        publisher = publisher,
        isFeatured = isFeatured
    )
}

/**
 * Data class for game detail API response
 */
data class GameDetailDto(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val genre: String,
    val platform: String,
    val releaseDate: String,
    val rating: Float,
    val developer: String,
    val publisher: String,
    val isFeatured: Boolean = false,
    val screenshots: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val systemRequirements: SystemRequirementsDto? = null,
    val tags: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val price: Double? = null,
    val websiteUrl: String? = null
) {
    /**
     * Converts DTO to domain model
     */
    fun toDomain(): GameDetail {
        return GameDetail(
            game = Game(
                id = id,
                title = title,
                description = description,
                thumbnailUrl = imageUrl,
                genre = genre.toGenre(),
                platform = platform.toPlatform(),
                releaseDate = releaseDate.formatReleaseDate(),
                rating = rating.sanitizeRating().formatRating(),
                developer = developer,
                publisher = publisher,
                isFeatured = isFeatured
            ),
            screenshots = screenshots,
            videos = videos,
            systemRequirements = systemRequirements?.toDomain(),
            tags = tags,
            languages = languages,
            price = price,
            websiteUrl = websiteUrl
        )
    }
}

/**
 * Data class for system requirements DTO
 */
data class SystemRequirementsDto(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String
) {
    /**
     * Converts DTO to domain model
     */
    fun toDomain(): com.example.core.model.SystemRequirements {
        return com.example.core.model.SystemRequirements(
            os = os,
            processor = processor,
            memory = memory,
            graphics = graphics,
            storage = storage
        )
    }
}