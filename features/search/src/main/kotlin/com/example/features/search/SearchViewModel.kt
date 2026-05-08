package com.example.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.database.repository.GameLocalRepository
import com.example.core.database.repository.UserLocalRepository
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Search screen.
 * Handles search queries, history, and filtering.
 */
class SearchViewModel(
    private val gameRepository: GameLocalRepository,
    private val userRepository: UserLocalRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    private val _effects = MutableSharedFlow<SearchEffect>()
    val effects = _effects.asSharedFlow()
    
    private var searchJob: Job? = null
    private var currentUserId: Long? = null
    
    // In-memory search history (in production, this would be persisted)
    private val searchHistory = mutableListOf<SearchHistoryItem>()
    
    init {
        loadSearchHistory()
        loadSuggestions()
        observeCurrentUser()
    }
    
    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.UpdateQuery -> updateQuery(intent.query)
            is SearchIntent.PerformSearch -> performSearch()
            is SearchIntent.ClearQuery -> clearQuery()
            is SearchIntent.SelectRecentSearch -> selectRecentSearch(intent.query)
            is SearchIntent.ClearSearchHistory -> clearSearchHistory()
            is SearchIntent.RemoveFromHistory -> removeFromHistory(intent.query)
            is SearchIntent.SelectGenre -> selectGenre(intent.genre)
            is SearchIntent.SelectPlatform -> selectPlatform(intent.platform)
            is SearchIntent.GameClicked -> navigateToGameDetail(intent.gameId)
            is SearchIntent.ToggleFavourite -> toggleFavourite(intent.gameId)
            is SearchIntent.LoadSuggestions -> loadSuggestions()
            is SearchIntent.ClearError -> clearError()
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
    
    private fun loadFavourites(userId: Long) {
        viewModelScope.launch {
            when (val result = userRepository.getFavourites(userId)) {
                is Result.Success -> {
                    val favouriteIds = result.data.map { it.gameId }.toSet()
                    _uiState.update { it.copy(favouriteIds = favouriteIds) }
                }
                is Result.Error -> { /* Silent fail */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun loadSearchHistory() {
        _uiState.update {
            it.copy(searchHistory = searchHistory.toList())
        }
    }
    
    private fun loadSuggestions() {
        viewModelScope.launch {
            // Load some popular games as suggestions
            when (val result = gameRepository.getFeaturedGames()) {
                is Result.Success -> {
                    _uiState.update { it.copy(suggestedGames = result.data.take(6)) }
                }
                is Result.Error -> { /* Silent fail */ }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        
        // Debounce search for autocomplete
        if (query.length >= 2) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(300) // Debounce delay
                searchLocally(query)
            }
        } else {
            _uiState.update { it.copy(searchResults = emptyList(), hasSearched = false) }
        }
    }
    
    private fun searchLocally(query: String) {
        viewModelScope.launch {
            when (val result = gameRepository.searchGamesLocally(query)) {
                is Result.Success -> {
                    val filteredResults = filterResults(result.data)
                    _uiState.update {
                        it.copy(
                            searchResults = filteredResults,
                            hasSearched = true,
                            isSearching = false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isSearching = false,
                            hasSearched = true
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isSearching = true) }
                }
            }
        }
    }
    
    private fun performSearch() {
        val query = _uiState.value.query
        if (query.isBlank()) return
        
        searchJob?.cancel()
        
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, error = null) }
            
            // Perform search
            when (val result = gameRepository.searchGamesLocally(query)) {
                is Result.Success -> {
                    val filteredResults = filterResults(result.data)
                    
                    // Add to search history
                    val historyItem = SearchHistoryItem(
                        query = query,
                        timestamp = System.currentTimeMillis(),
                        resultCount = filteredResults.size
                    )
                    searchHistory.removeAll { it.query.equals(query, ignoreCase = true) }
                    searchHistory.add(0, historyItem)
                    // Keep only last 10 items
                    if (searchHistory.size > 10) {
                        searchHistory.removeAt(searchHistory.lastIndex)
                    }
                    
                    _uiState.update {
                        it.copy(
                            searchResults = filteredResults,
                            hasSearched = true,
                            isSearching = false,
                            searchHistory = searchHistory.toList()
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isSearching = false,
                            hasSearched = true
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isSearching = true) }
                }
            }
        }
    }
    
    private fun filterResults(games: List<com.example.core.model.Game>): List<com.example.core.model.Game> {
        val state = _uiState.value
        var filtered = games
        
        // Apply genre filter
        state.selectedGenre?.let { genre ->
            filtered = filtered.filter { it.genre == genre }
        }
        
        // Apply platform filter
        state.selectedPlatform?.let { platform ->
            filtered = filtered.filter { it.platform == platform }
        }
        
        return filtered
    }
    
    private fun clearQuery() {
        _uiState.update {
            it.copy(
                query = "",
                searchResults = emptyList(),
                hasSearched = false,
                isSearching = false
            )
        }
        searchJob?.cancel()
    }
    
    private fun selectRecentSearch(query: String) {
        _uiState.update { it.copy(query = query) }
        performSearch()
    }
    
    private fun clearSearchHistory() {
        searchHistory.clear()
        _uiState.update { it.copy(searchHistory = emptyList()) }
    }
    
    private fun removeFromHistory(query: String) {
        searchHistory.removeAll { it.query.equals(query, ignoreCase = true) }
        _uiState.update { it.copy(searchHistory = searchHistory.toList()) }
    }
    
    private fun selectGenre(genre: Genre?) {
        _uiState.update { it.copy(selectedGenre = genre) }
        
        // Re-filter current results
        if (_uiState.value.hasSearched) {
            viewModelScope.launch {
                val filteredResults = filterResults(_uiState.value.searchResults)
                _uiState.update { it.copy(searchResults = filteredResults) }
            }
        }
    }
    
    private fun selectPlatform(platform: Platform?) {
        _uiState.update { it.copy(selectedPlatform = platform) }
        
        // Re-filter current results
        if (_uiState.value.hasSearched) {
            viewModelScope.launch {
                val filteredResults = filterResults(_uiState.value.searchResults)
                _uiState.update { it.copy(searchResults = filteredResults) }
            }
        }
    }
    
    private fun navigateToGameDetail(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(SearchEffect.NavigateToGameDetail(gameId))
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
                    
                    val game = _uiState.value.searchResults.find { it.id == gameId }
                        ?: _uiState.value.suggestedGames.find { it.id == gameId }
                    
                    val gameTitle = game?.title ?: "Game"
                    
                    if (isFavourited) {
                        _effects.emit(SearchEffect.ShowFavouriteRemoved(gameTitle))
                    } else {
                        _effects.emit(SearchEffect.ShowFavouriteAdded(gameTitle))
                    }
                }
                is Result.Error -> {
                    _effects.emit(SearchEffect.ShowError(result.message))
                }
                is Result.Loading -> { /* Ignore */ }
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}