package com.example.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Result
import com.example.core.repository.GameRepository
import com.example.core.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Game Detail screen.
 * Displays detailed information about a game.
 */
class DetailViewModel(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameDetailUiState())
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<DetailEffect>()
    val effects = _effects.asSharedFlow()
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: GameDetailIntent) {
        when (intent) {
            is GameDetailIntent.LoadGame -> loadGame(intent.gameId)
            is GameDetailIntent.RefreshGame -> refreshGame()
            is GameDetailIntent.ToggleFavourite -> toggleFavourite()
            is GameDetailIntent.GameClicked -> showScreenshot(intent.screenshotUrl)
            is GameDetailIntent.VisitWebsite -> visitWebsite()
            is GameDetailIntent.ClearError -> clearError()
        }
    }
    
    private fun loadGame(gameId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(gameId = gameId, isLoading = true, error = null) }
            
            // Load game details
            when (val result = gameRepository.getGameById(gameId)) {
                is Result.Success -> {
                    val game = result.data
                    _uiState.update {
                        it.copy(
                            game = game,
                            screenshots = game.screenshots,
                            minimumRequirements = game.minimumRequirements ?: "",
                            recommendedRequirements = game.recommendedRequirements ?: ""
                        )
                    }
                    
                    // Check if game is in favourites
                    checkFavouriteStatus(gameId)
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
    
    private fun refreshGame() {
        val gameId = _uiState.value.gameId
        if (gameId > 0) {
            loadGame(gameId)
        }
    }
    
    private fun checkFavouriteStatus(gameId: Long) {
        viewModelScope.launch {
            userRepository.getUserGame(gameId).collect { userGame ->
                _uiState.update {
                    it.copy(
                        userGame = userGame,
                        isFavourite = userGame != null,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun toggleFavourite() {
        viewModelScope.launch {
            val game = _uiState.value.game ?: return@launch
            
            if (_uiState.value.isFavourite) {
                userRepository.removeFavourite(game.id)
                _effects.emit(DetailEffect.ShowMessage("Removed from favourites"))
            } else {
                userRepository.addToFavourites(game)
                _effects.emit(DetailEffect.ShowMessage("Added to favourites"))
            }
            
            // Update favourite status
            checkFavouriteStatus(game.id)
        }
    }
    
    private fun showScreenshot(screenshotUrl: String) {
        viewModelScope.launch {
            _effects.emit(DetailEffect.ShowScreenshot(screenshotUrl))
        }
    }
    
    private fun visitWebsite() {
        val game = _uiState.value.game ?: return
        game.gameUrl?.let { url ->
            viewModelScope.launch {
                _effects.emit(DetailEffect.OpenUrl(url))
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}