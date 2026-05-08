package com.example.feature.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.UserRepository
import com.example.core.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Favourites screen.
 * Displays the user's favourite games.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState: StateFlow<FavouritesUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<FavouritesEffect>()
    val effects = _effects.asSharedFlow()
    
    init {
        loadFavourites()
    }
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: FavouritesIntent) {
        when (intent) {
            is FavouritesIntent.LoadFavourites -> loadFavourites()
            is FavouritesIntent.RefreshFavourites -> loadFavourites()
            is FavouritesIntent.GameClicked -> navigateToGame(intent.gameId)
            is FavouritesIntent.RemoveFavourite -> removeFavourite(intent.gameId)
            is FavouritesIntent.ClearError -> clearError()
        }
    }
    
    private fun loadFavourites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            userRepository.getFavourites().collect { favourites ->
                _uiState.update {
                    it.copy(
                        favourites = favourites,
                        isLoading = false,
                        isEmpty = favourites.isEmpty()
                    )
                }
            }
        }
    }
    
    private fun navigateToGame(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(FavouritesEffect.NavigateToGameDetail(gameId))
        }
    }
    
    private fun removeFavourite(gameId: Long) {
        viewModelScope.launch {
            when (val result = userRepository.removeFavourite(gameId)) {
                is Result.Success -> {
                    _effects.emit(FavouritesEffect.ShowMessage("Removed from favourites"))
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