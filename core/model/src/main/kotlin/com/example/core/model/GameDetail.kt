package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents detailed information about a game.
 * Extends the basic Game model with additional details.
 */
@Serializable
data class GameDetail(
    val game: Game,
    val screenshots: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val systemRequirements: SystemRequirements? = null,
    val tags: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val price: Double? = null,
    val websiteUrl: String? = null
)

/**
 * Represents system requirements for PC games
 */
@Serializable
data class SystemRequirements(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String
)