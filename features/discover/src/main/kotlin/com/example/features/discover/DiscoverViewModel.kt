package com.example.features.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.repository.GameLocalRepository
import com.example.core.database.repository.UserLocalRepository
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Discover screen.
 * Manages UI state and handles user intents.
 */
class DiscoverViewModel(
    private val gameRepository: GameLocalRepository,
    private val userRepository: UserLocalRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<DiscoverEffect>()
    val effects = _effects.asSharedFlow()
    
    private var currentUserId: Long? = null
    
    init {
        handleIntent(DiscoverIntent.LoadInitialData)
        observeCurrentUser()
    }
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: DiscoverIntent) {
        when (intent) {
            is DiscoverIntent.LoadInitialData -> loadInitialData()
            is DiscoverIntent.RefreshData -> refreshData()
            is DiscoverIntent.SelectGenre -> selectGenre(intent.genre)
            is DiscoverIntent.SelectPlatform -> selectPlatform(intent.platform)
            is DiscoverIntent.GameClicked -> navigateToGameDetail(intent.gameId)
            is DiscoverIntent.ToggleFavourite -> toggleFavourite(intent.gameId)
            is DiscoverIntent.ClearError -> clearError()
        }
    }
    
    private fun observeCurrentUser() {
        viewModelScope.launch {
            userRepository.observeCurrentUser().collect { user ->
                currentUserId = user?.id
                user?.let { loadFavourites(it.id) }
            }
        }
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // Load featured games
            loadFeaturedGames()
            
            // Load popular games
            loadPopularGames()
            
            // Load recently added
            loadRecentlyAdded()
            
            _uiState.update { it.copy(isLoading = false) }
        }
    }
    
    private fun refreshData() {
        loadInitialData()
    }
    
    private fun loadFeaturedGames() {
        viewModelScope.launch {
            when (val result = gameRepository.getFeaturedGames()) {
                is Result.Success -> {
                    _uiState.update { it.copy(featuredGames = result.data) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }
                is Result.Loading -> { /* Handled by isLoading state */ }
            }
        }
    }
    
    private fun loadPopularGames() {
        viewModelScope.launch {
            when (val result = gameRepository.getGames(page = 1, pageSize = 10)) {
                is Result.Success -> {
                    _uiState.update { it.copy(popularGames = result.data) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }
                is Result.Loading -> { /* Handled by isLoading state */ }
            }
        }
    }
    
    private fun loadRecentlyAdded() {
        viewModelScope.launch {
            when (val result = gameRepository.getGames(page = 1, pageSize = 5)) {
                is Result.Success -> {
                    _uiState.update { it.copy(recentlyAdded = result.data) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }
                is Result.Loading -> { /* Handled by isLoading state */ }
            }
        }
    }
    
    private fun loadFavourites(userId: Long) {
        viewModelScope.launch {
            when (val result = userRepository.getFavourites(userId)) {
                is Result.Success -> {
                    val favouriteIds = result.data.map { it.gameId }.toSet()
                    _uiState.update { it.copy(favouriteIds = favouriteIds) }
                }
                is Result.Error -> {
                    // Silent fail for favourites loading
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun selectGenre(genre: Genre?) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedGenre = genre) }
            
            if (genre != null) {
                when (val result = gameRepository.getGamesByGenre(genre.name)) {
                    is Result.Success -> {
                        _uiState.update { it.copy(popularGames = result.data) }
                    }
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Loading -> { /* Ignore */ }
                }
            } else {
                loadPopularGames()
            }
        }
    }
    
    private fun selectPlatform(platform: Platform?) {
        viewModelScope.update { it.copy(selectedPlatform = platform) }
        
        viewModelScope.launch {
            if (platform != null) {
                when (val result = gameRepository.getGamesByPlatform(platform.name)) {
                    is Result.Success -> {
                        _uiState.update { it.copy(popularGames = result.data) }
                    }
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Loading -> { /* Ignore */ }
                }
            } else {
                loadPopularGames()
            }
        }
    }
    
    private fun navigateToGameDetail(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(DiscoverEffect.NavigateToGameDetail(gameId))
        }
    }
    
    private fun toggleFavourite(gameId: Long) {
        val userId = currentUserId ?: return
        
        viewModelScope.launch {
            val isFavourited = _uiState.value.favouriteIds.contains(gameId)
            
            val result = if (isFavourited) {
                userRepository.removeFavourite(userId, gameId)
            } else {
                userRepository.addFavourite(userId, gameId).map { 1L }
            }
            
            when (result) {
                is Result.Success -> {
                    _uiState.update { state ->
                        val newFavouriteIds = if (isFavourited) {
                            state.favouriteIds - gameId
                        } else {
                            state.favouriteIds + gameId
                        }
                        state.copy(favouriteIds = newFavouriteIds)
                    }
                    
                    val game = _uiState.value.featuredGames.find { it.id == gameId }
                        ?: _uiState.value.popularGames.find { it.id == gameId }
                        ?: _uiState.value.recentlyAdded.find { it.id == gameId }
                    
                    val gameTitle = game?.title ?: "Game"
                    
                    if (isFavourited) {
                        _effects.emit(DiscoverEffect.ShowFavouriteRemoved(gameTitle))
                    } else {
                        _effects.emit(DiscoverEffect.ShowFavouriteAdded(gameTitle))
                    }
                }
                is Result.Error -> {
                    _effects.emit(DiscoverEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}