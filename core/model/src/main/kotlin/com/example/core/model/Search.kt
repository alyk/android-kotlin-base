package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * Represents search filter criteria
 */
@Serializable
data class SearchFilter(
    val query: String = "",
    val genres: List<Genre> = emptyList(),
    val platforms: List<Platform> = emptyList(),
    val minRating: Float? = null,
    val releaseYear: Int? = null,
    val sortBy: SortOption = SortOption.RELEVANCE
)

/**
 * Sorting options for search results
 */
@Serializable
enum class SortOption(val displayName: String) {
    RELEVANCE("Relevance"),
    RATING("Rating"),
    RELEASE_DATE("Release Date"),
    ALPHABETICAL("Alphabetical")
}

/**
 * Represents the result of a search operation
 */
@Serializable
data class SearchResult(
    val games: List<Game>,
    val totalCount: Int,
    val page: Int,
    val pageSize: Int,
    val hasMore: Boolean
)