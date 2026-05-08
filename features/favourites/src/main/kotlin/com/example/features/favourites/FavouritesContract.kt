package com.example.features.favourites

import com.example.core.model.Game
import com.example.core.model.UserGame

/**
 * UI State for the Favourites screen
 */
data class FavouritesUiState(
    val isLoading: Boolean = false,
    val favouriteGames: List<UserGame> = emptyList(),
    val error: String? = null,
    val isEmpty: Boolean = true
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
    data class ShowError(val message: String) : FavouritesEffect()
    data class ShowFavouriteRemoved(val gameTitle: String) : FavouritesEffect()
}