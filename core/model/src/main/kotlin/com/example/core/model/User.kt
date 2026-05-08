package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a user in the domain model.
 * Used primarily for favourites and user preferences.
 */
@Serializable
data class User(
    val id: Long,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val createdAt: String,
    val preferences: UserPreferences = UserPreferences()
)

/**
 * User preferences for personalization
 */
@Serializable
data class UserPreferences(
    val favouriteGenres: List<Genre> = emptyList(),
    val favouritePlatforms: List<Platform> = emptyList(),
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false
)