package com.example.feature.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.repository.FavouritesRepository
import com.example.core.model.LocalFavourite
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
 * Manages device-local favourite games.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val uiState: StateFlow<FavouritesUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<FavouritesEffect>()
    val effects = _effects.asSharedFlow()

    init {
        observeFavourites()
    }

    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: FavouritesIntent) {
        when (intent) {
            is FavouritesIntent.LoadFavourites -> loadFavourites()
            is FavouritesIntent.RefreshFavourites -> refreshFavourites()
            is FavouritesIntent.AddFavourite -> addFavourite(intent.game)
            is FavouritesIntent.GameClicked -> navigateToGame(intent.gameId)
            is FavouritesIntent.RemoveFavourite -> removeFavourite(intent.gameId)
            is FavouritesIntent.ClearError -> clearError()
        }
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            favouritesRepository.observeAllFavourites().collect { favourites ->
                _uiState.value = FavouritesUiState.Success(favourites)
            }
        }
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            _uiState.value = FavouritesUiState.Loading

            when (val result = favouritesRepository.getAllFavourites()) {
                is Result.Success -> {
                    _uiState.value = FavouritesUiState.Success(result.data)
                }
                is Result.Error -> {
                    _uiState.value = FavouritesUiState.Error(result.message ?: "Unknown error")
                }
                is Result.Loading -> {
                    _uiState.value = FavouritesUiState.Loading
                }
            }
        }
    }

    private fun refreshFavourites() {
        loadFavourites()
    }

    private fun addFavourite(game: LocalFavourite) {
        viewModelScope.launch {
            when (val result = favouritesRepository.addFavourite(game)) {
                is Result.Success -> {
                    _effects.emit(FavouritesEffect.ShowFavouriteAdded(game.title))
                }
                is Result.Error -> {
                    _effects.emit(FavouritesEffect.ShowError(result.message ?: "Failed to add favourite"))
                }
                is Result.Loading -> { /* Ignore */ }
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
            val currentState = _uiState.value
            if (currentState is FavouritesUiState.Success) {
                val favouriteGame = currentState.favouriteGames.find { it.gameId == gameId }
                val gameTitle = favouriteGame?.title ?: "Game"

                when (val result = favouritesRepository.removeFavourite(gameId)) {
                    is Result.Success -> {
                        _uiState.value = FavouritesUiState.Success(
                            currentState.favouriteGames.filter { it.gameId != gameId }
                        )
                        _effects.emit(FavouritesEffect.ShowFavouriteRemoved(gameTitle))
                    }
                    is Result.Error -> {
                        _effects.emit(FavouritesEffect.ShowError(result.message ?: "Failed to remove favourite"))
                    }
                    is Result.Loading -> { /* Ignore */ }
                }
            }
        }
    }

    private fun clearError() {
        _uiState.value = FavouritesUiState.Loading
        loadFavourites()
    }
}