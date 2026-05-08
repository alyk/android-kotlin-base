package com.example.feature.search

import com.example.core.model.Game

/**
 * UI State for the Search screen
 */
data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<Game> = emptyList(),
    val error: String? = null,
    val hasSearched: Boolean = false,
    val isEmpty: Boolean = false
)

/**
 * Intents/Events from the Search screen
 */
sealed class SearchIntent {
    data class UpdateQuery(val query: String) : SearchIntent()
    data object ClearQuery : SearchIntent()
    data object Search : SearchIntent()
    data object RefreshSearch : SearchIntent()
    data class GameClicked(val gameId: Long) : SearchIntent()
    data object ClearError : SearchIntent()
}

/**
 * Side effects from the Search screen
 */
sealed class SearchEffect {
    data class NavigateToGameDetail(val gameId: Long) : SearchEffect()
    data class ShowError(val message: String) : SearchEffect()
}