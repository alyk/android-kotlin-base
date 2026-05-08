package com.example.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.GameRepository
import com.example.core.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Search screen.
 * Handles search queries and displays game results.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<SearchEffect>()
    val effects = _effects.asSharedFlow()

    private var searchJob: Job? = null

    /**
     * Handles user intents and updates state accordingly
     */
    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.UpdateQuery -> updateQuery(intent.query)
            is SearchIntent.ClearQuery -> clearQuery()
            is SearchIntent.Search -> search()
            is SearchIntent.RefreshSearch -> search()
            is SearchIntent.GameClicked -> navigateToGame(intent.gameId)
            is SearchIntent.ClearError -> clearError()
        }
    }

    private fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }

        // Debounce search
        searchJob?.cancel()
        if (query.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(300) // Debounce delay
                search()
            }
        } else {
            _uiState.update {
                it.copy(
                    searchResults = emptyList(),
                    hasSearched = false,
                    isEmpty = false
                )
            }
        }
    }

    private fun clearQuery() {
        searchJob?.cancel()
        _uiState.update {
            it.copy(
                query = "",
                searchResults = emptyList(),
                hasSearched = false,
                isEmpty = false
            )
        }
    }

    private fun search() {
        val query = _uiState.value.query
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = gameRepository.searchGames(query)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            searchResults = result.data.games,
                            isLoading = false,
                            hasSearched = true,
                            isEmpty = result.data.games.isEmpty()
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            hasSearched = true
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun navigateToGame(gameId: Long) {
        viewModelScope.launch {
            _effects.emit(SearchEffect.NavigateToGameDetail(gameId))
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}