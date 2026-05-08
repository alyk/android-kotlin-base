package com.example.features.detail

import com.example.core.model.Game
import com.example.core.model.Screenshot

/**
 * UI State for the Game Detail screen
 */
data class DetailUiState(
    val isLoading: Boolean = false,
    val game: Game? = null,
    val screenshots: List<Screenshot> = emptyList(),
    val similarGames: List<Game> = emptyList(),
    val isFavourited: Boolean = false,
    val error: String? = null,
    val isInLibrary: Boolean = false
)

/**
 * Intents/Events from the Game Detail screen
 */
sealed class DetailIntent {
    data class LoadGameDetail(val gameId: Long) : DetailIntent()
    data object RefreshDetail : DetailIntent()
    data object ToggleFavourite : DetailIntent()
    data class GameClicked(val gameId: Long) : DetailIntent()
    data object OpenWebsite : DetailIntent()
    data object ShareGame : DetailIntent()
    data object AddToLibrary : DetailIntent()
    data object RemoveFromLibrary : DetailIntent()
    data object ClearError : DetailIntent()
}

/**
 * Side effects from the Game Detail screen
 */
sealed class DetailEffect {
    data class NavigateToGameDetail(val gameId: Long) : DetailEffect()
    data class OpenUrl(val url: String) : DetailEffect()
    data class ShareGameInfo(val game: Game) : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
    data class ShowFavouriteAdded(val gameTitle: String) : DetailEffect()
    data class ShowFavouriteRemoved(val gameTitle: String) : DetailEffect()
    data class ShowAddedToLibrary(val gameTitle: String) : DetailEffect()
    data class ShowRemovedFromLibrary(val gameTitle: String) : DetailEffect()
    data object NavigateBack : DetailEffect()
}