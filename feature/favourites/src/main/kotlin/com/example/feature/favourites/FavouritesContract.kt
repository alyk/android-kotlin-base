package com.example.feature.favourites

import com.example.core.model.Game

/**
 * UI State for the Favourites screen
 */
data class FavouritesUiState(
    val favourites: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false
)

/**
 * Intents/Events from the Favourites screen
 */
sealed class FavouritesIntent {
    data object LoadFavourites : FavouritesIntent()
    data object RefreshFavourites : FavouritesIntent()
    data class GameClicked(val gameId: Long) : FavouritesIntent()
    data class RemoveFavourite(val gameId: Long) : FavouritesIntent()
    data object ClearError : FavouritesIntent()
}

/**
 * Side effects from the Favourites screen
 */
sealed class FavouritesEffect {
    data class NavigateToGameDetail(val gameId: Long) : FavouritesEffect()
    data class ShowMessage(val message: String) : FavouritesEffect()
}