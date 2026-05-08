package com.example.feature.discover

import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform

/**
 * UI State for the Discover screen
 */
data class DiscoverUiState(
    val isLoading: Boolean = false,
    val featuredGames: List<Game> = emptyList(),
    val popularGames: List<Game> = emptyList(),
    val recentlyAdded: List<Game> = emptyList(),
    val selectedGenre: Genre? = null,
    val selectedPlatform: Platform? = null,
    val error: String? = null,
    val favouriteIds: Set<Long> = emptySet()
)

/**
 * Intents/Events from the Discover screen
 */
sealed class DiscoverIntent {
    data object LoadInitialData : DiscoverIntent()
    data object RefreshData : DiscoverIntent()
    data class SelectGenre(val genre: Genre?) : DiscoverIntent()
    data class SelectPlatform(val platform: Platform?) : DiscoverIntent()
    data class GameClicked(val gameId: Long) : DiscoverIntent()
    data class ToggleFavourite(val gameId: Long) : DiscoverIntent()
    data object ClearError : DiscoverIntent()
}

/**
 * Side effects from the Discover screen
 */
sealed class DiscoverEffect {
    data class NavigateToGameDetail(val gameId: Long) : DiscoverEffect()
    data class ShowError(val message: String) : DiscoverEffect()
    data class ShowFavouriteAdded(val gameTitle: String) : DiscoverEffect()
    data class ShowFavouriteRemoved(val gameTitle: String) : DiscoverEffect()
}
