package com.example.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.repository.GameLocalRepository
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
 * ViewModel for the Game Detail screen.
 * Manages game details, screenshots, and related actions.
 */
class DetailViewModel(
    private val gameRepository: GameLocalRepository,
    private val userRepository: UserLocalRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<DetailEffect>()
    val effects = _effects.asSharedFlow()
    
    private var currentUserId: Long? = null
    private var currentGameId: Long? = null
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadGameDetail -> loadGameDetail(intent.gameId)
            is DetailIntent.RefreshDetail -> refreshDetail()
            is DetailIntent.ToggleFavourite -> toggleFavourite()
            is DetailIntent.GameClicked -> navigateToGame(intent.gameId)
            is DetailIntent.OpenWebsite -> openWebsite()
            is DetailIntent.ShareGame -> shareGame()
            is DetailIntent.AddToLibrary -> addToLibrary()
            is DetailIntent.RemoveFromLibrary -> removeFromLibrary()
            is DetailIntent.ClearError -> clearError()
        }
    }
    
    private fun observeCurrentUser() {
        viewModelScope.launch {
            userRepository.observeCurrentUser().collect { user ->
                currentUserId = user?.id
                user?.let { checkFavouriteStatus(it.id) }
                user?.let { checkLibraryStatus(it.id) }
            }
        }
    }
    
    private fun loadGameDetail(gameId: Long) {
        currentGameId = gameId
        observeCurrentUser()
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // Load game details
            when (val result = gameRepository.getGameById(gameId)) {
                is Result.Success -> {
                    val game = result.data
                    _uiState.update { it.copy(game = game, isLoading = false) }
                    
                    // Load screenshots if available
                    loadScreenshots(gameId)
                    
                    // Load similar games
                    loadSimilarGames(game)
                    
                    // Check favourite status
                    currentUserId?.let { checkFavouriteStatus(it) }
                    
                    // Check library status
                    currentUserId?.let { checkLibraryStatus(it) }
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
    
    private fun loadScreenshots(gameId: Long) {
        viewModelScope.launch {
            when (val result = gameRepository.getScreenshots(gameId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(screenshots = result.data) }
                }
                is Result.Error -> { /* Screenshots are optional */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun loadSimilarGames(game: com.example.core.model.Game) {
        viewModelScope.launch {
            // Get games from the same genre
            when (val result = gameRepository.getGamesByGenre(game.genre.name)) {
                is Result.Success -> {
                    // Exclude current game and take first 4
                    val similar = result.data.filter { it.id != game.id }.take(4)
                    _uiState.update { it.copy(similarGames = similar) }
                }
                is Result.Error -> { /* Similar games are optional */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun refreshDetail() {
        currentGameId?.let { loadGameDetail(it) }
    }
    
    private fun checkFavouriteStatus(userId: Long) {
        val gameId = currentGameId ?: return
        
        viewModelScope.launch {
            when (val result = userRepository.getFavourites(userId)) {
                is Result.Success -> {
                    val isFavourited = result.data.any { it.gameId == gameId }
                    _uiState.update { it.copy(isFavourited = isFavourited) }
                }
                is Result.Error -> { /* Silent fail */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun checkLibraryStatus(userId: Long) {
        val gameId = currentGameId ?: return
        
        viewModelScope.launch {
            when (val result = userRepository.getLibrary(userId)) {
                is Result.Success -> {
                    val isInLibrary = result.data.any { it.gameId == gameId }
                    _uiState.update { it.copy(isInLibrary = isInLibrary) }
                }
                is Result.Error -> { /* Silent fail */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun toggleFavourite() {
        val userId = currentUserId ?: return
        val gameId = currentGameId ?: return
        val game = _uiState.value.game ?: return
        
        viewModelScope.launch {
            val isFavourited = _uiState.value.isFavourited
            
            val result = if (isFavourited) {
                userRepository.removeFavourite(userId, gameId)
            } else {
                userRepository.addFavourite(userId, gameId).map { 1L }
            }
            
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isFavourited = !isFavourited) }
                    
                    if (isFavourited) {
                        _effects.emit(DetailEffect.ShowFavouriteRemoved(game.title))
                    } else {
                        _effects.emit(DetailEffect.ShowFavouriteAdded(game.title))
                    }
                }
                is Result.Error -> {
                    _effects.emit(DetailEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun navigateToGame(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(DetailEffect.NavigateToGameDetail(gameId))
        }
    }
    
    private fun openWebsite() {
        val game = _uiState.value.game ?: return
        val url = game.gameUrl ?: return
        
        viewModelScope.launch {
            _effects.emit(DetailEffect.OpenUrl(url))
        }
    }
    
    private fun shareGame() {
        val game = _uiState.value.game ?: return
        
        viewModelScope.launch {
            _effects.emit(DetailEffect.ShareGameInfo(game))
        }
    }
    
    private fun addToLibrary() {
        val userId = currentUserId ?: return
        val gameId = currentGameId ?: return
        val game = _uiState.value.game ?: return
        
        viewModelScope.launch {
            when (val result = userRepository.addToLibrary(userId, gameId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isInLibrary = true) }
                    _effects.emit(DetailEffect.ShowAddedToLibrary(game.title))
                }
                is Result.Error -> {
                    _effects.emit(DetailEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun removeFromLibrary() {
        val userId = currentUserId ?: return
        val gameId = currentGameId ?: return
        val game = _uiState.value.game ?: return
        
        viewModelScope.launch {
            when (val result = userRepository.removeFromLibrary(userId, gameId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isInLibrary = false) }
                    _effects.emit(DetailEffect.ShowRemovedFromLibrary(game.title))
                }
                is Result.Error -> {
                    _effects.emit(DetailEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}