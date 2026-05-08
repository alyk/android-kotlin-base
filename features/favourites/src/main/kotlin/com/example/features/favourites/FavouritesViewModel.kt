package com.example.features.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.repository.UserLocalRepository
import com.example.core.model.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Favourites screen.
 * Manages user's favourite games.
 */
class FavouritesViewModel(
    private val userRepository: UserLocalRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState: StateFlow<FavouritesUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<FavouritesEffect>()
    val effects = _effects.asSharedFlow()
    
    private var currentUserId: Long? = null
    
    init {
        observeCurrentUser()
    }
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: FavouritesIntent) {
        when (intent) {
            is FavouritesIntent.LoadFavourites -> loadFavourites()
            is FavouritesIntent.RefreshFavourites -> refreshFavourites()
            is FavouritesIntent.GameClicked -> navigateToGame(intent.gameId)
            is FavouritesIntent.RemoveFavourite -> removeFavourite(intent.gameId)
            is FavouritesIntent.ClearError -> clearError()
        }
    }
    
    private fun observeCurrentUser() {
        viewModelScope.launch {
            userRepository.observeCurrentUser().collect { user ->
                currentUserId = user?.id
                if (user != null) {
                    loadFavourites()
                }
            }
        }
    }
    
    private fun loadFavourites() {
        val userId = currentUserId ?: return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            when (val result = userRepository.getFavourites(userId)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            favouriteGames = result.data,
                            isLoading = false,
                            isEmpty = result.data.isEmpty()
                        )
                    }
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
    
    private fun refreshFavourites() {
        loadFavourites()
    }
    
    private fun navigateToGame(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(FavouritesEffect.NavigateToGameDetail(gameId))
        }
    }
    
    private fun removeFavourite(gameId: Long) {
        val userId = currentUserId ?: return
        
        viewModelScope.launch {
            val favouriteGame = _uiState.value.favouriteGames.find { it.gameId == gameId }
            val gameTitle = favouriteGame?.game?.title ?: "Game"
            
            when (val result = userRepository.removeFavourite(userId, gameId)) {
                is Result.Success -> {
                    _uiState.update { state ->
                        val updatedList = state.favouriteGames.filter { it.gameId != gameId }
                        state.copy(
                            favouriteGames = updatedList,
                            isEmpty = updatedList.isEmpty()
                        )
                    }
                    _effects.emit(FavouritesEffect.ShowFavouriteRemoved(gameTitle))
                }
                is Result.Error -> {
                    _effects.emit(FavouritesEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}