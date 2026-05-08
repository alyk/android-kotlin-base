package com.example.feature.detail

import com.example.core.model.Game
import com.example.core.model.UserGame

/**
 * UI State for the Game Detail screen
 */
data class GameDetailUiState(
    val gameId: Long = 0,
    val game: Game? = null,
    val userGame: UserGame? = null,
    val isLoading: Boolean = false,
    val isFavourite: Boolean = false,
    val error: String? = null,
    val screenshots: List<String> = emptyList(),
    val minimumRequirements: String = "",
    val recommendedRequirements: String = ""
)

/**
 * Intents/Events from the Game Detail screen
 */
sealed class GameDetailIntent {
    data class LoadGame(val gameId: Long) : GameDetailIntent()
    data object RefreshGame : GameDetailIntent()
    data object ToggleFavourite : GameDetailIntent()
    data class GameClicked(val screenshotUrl: String) : GameDetailIntent()
    data object VisitWebsite : GameDetailIntent()
    data object ClearError : GameDetailIntent()
}

/**
 * Side effects from the Game Detail screen
 */
sealed class GameDetailEffect {
    data class ShowScreenshot(val url: String) : GameDetailEffect()
    data class OpenUrl(val url: String) : GameDetailEffect()
    data class ShowMessage(val message: String) : GameDetailEffect()
    data object NavigateBack : GameDetailEffect()
}