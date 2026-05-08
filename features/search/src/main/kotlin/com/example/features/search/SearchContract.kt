package com.example.features.search

import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform

/**
 * UI State for the Search screen
 */
data class SearchUiState(
    val query: String = "",
    val searchResults: List<Game> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val suggestedGames: List<Game> = emptyList(),
    val selectedGenre: Genre? = null,
    val selectedPlatform: Platform? = null,
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false,
    val error: String? = null,
    val favouriteIds: Set<Long> = emptySet(),
    val searchHistory: List<SearchHistoryItem> = emptyList()
)

/**
 * Data class for search history items
 */
data class SearchHistoryItem(
    val query: String,
    val timestamp: Long,
    val resultCount: Int = 0
)

/**
 * Intents/Events from the Search screen
 */
sealed class SearchIntent {
    data class UpdateQuery(val query: String) : SearchIntent()
    data object PerformSearch : SearchIntent()
    data object ClearQuery : SearchIntent()
    data class SelectRecentSearch(val query: String) : SearchIntent()
    data object ClearSearchHistory : SearchIntent()
    data class RemoveFromHistory(val query: String) : SearchIntent()
    data class SelectGenre(val genre: Genre?) : SearchIntent()
    data class SelectPlatform(val platform: Platform?) : SearchIntent()
    data class GameClicked(val gameId: Long) : SearchIntent()
    data class ToggleFavourite(val gameId: Long) : SearchIntent()
    data object LoadSuggestions : SearchIntent()
    data object ClearError : SearchIntent()
}

/**
 * Side effects from the Search screen
 */
sealed class SearchEffect {
    data class NavigateToGameDetail(val gameId: Long) : SearchEffect()
    data class ShowError(val message: String) : SearchEffect()
    data class ShowFavouriteAdded(val gameTitle: String) : SearchEffect()
    data class ShowFavouriteRemoved(val gameTitle: String) : SearchEffect()
}