package com.example.feature.favourites

import com.example.core.model.LocalFavourite

/**
 * UI State for the Favourites screen
 * Provides properties for easy access from Compose UI
 */
sealed interface FavouritesUiState {
    /**
     * Whether the UI is in loading state
     */
    val isLoading: Boolean

    /**
     * List of favourite games
     */
    val favouriteGames: List<LocalFavourite>

    /**
     * Error message if any
     */
    val error: String?

    /**
     * Loading state with empty games list
     */
    data object Loading : FavouritesUiState {
        override val isLoading: Boolean = true
        override val favouriteGames: List<LocalFavourite> = emptyList()
        override val error: String? = null
    }

    /**
     * Success state with games list
     */
    data class Success(
        override val favouriteGames: List<LocalFavourite>
    ) : FavouritesUiState {
        override val isLoading: Boolean = false
        override val error: String? = null
    }

    /**
     * Error state with message
     */
    data class Error(
        val message: String
    ) : FavouritesUiState {
        override val isLoading: Boolean = false
        override val favouriteGames: List<LocalFavourite> = emptyList()
        override val error: String? = message
    }
}

/**
 * Computed property for convenience - returns true when there are no games
 */
val FavouritesUiState.isEmpty: Boolean
    get() = favouriteGames.isEmpty()

/**
 * Intents/Events from the Favourites screen
 */
sealed class FavouritesIntent {
    data object LoadFavourites : FavouritesIntent()
    data object RefreshFavourites : FavouritesIntent()
    data class AddFavourite(val game: LocalFavourite) : FavouritesIntent()
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
    data class ShowFavouriteAdded(val gameTitle: String) : FavouritesEffect()
}
