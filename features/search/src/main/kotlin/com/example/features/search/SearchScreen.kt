package com.example.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.ui.components.EmptySearchScreen
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.GameCardHorizontal
import com.example.core.ui.components.GenreFilterChip
import com.example.core.ui.components.LoadingIndicator
import com.example.core.ui.components.PlatformFilterChip
import com.example.core.ui.components.SearchBar
import kotlinx.coroutines.flow.collectLatest

/**
 * Search screen composable with search bar, filters, and results.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onGameClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is SearchEffect.NavigateToGameDetail -> {
                    onGameClick(effect.gameId)
                }
                is SearchEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is SearchEffect.ShowFavouriteAdded -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} added to favourites")
                }
                is SearchEffect.ShowFavouriteRemoved -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} removed from favourites")
                }
            }
        }
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            SearchBar(
                query = uiState.query,
                onQueryChange = { viewModel.handleIntent(SearchIntent.UpdateQuery(it)) },
                onSearch = { viewModel.handleIntent(SearchIntent.PerformSearch) },
                enabled = true
            )
            
            // Content based on state
            when {
                uiState.isSearching -> {
                    LoadingIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                }
                uiState.query.isEmpty() && !uiState.hasSearched -> {
                    SearchInitialContent(
                        suggestedGames = uiState.suggestedGames,
                        searchHistory = uiState.searchHistory,
                        favouriteIds = uiState.favouriteIds,
                        onGameClick = { viewModel.handleIntent(SearchIntent.GameClicked(it)) },
                        onFavouriteClick = { viewModel.handleIntent(SearchIntent.ToggleFavourite(it)) },
                        onRecentSearchClick = { viewModel.handleIntent(SearchIntent.SelectRecentSearch(it)) },
                        onClearHistoryClick = { viewModel.handleIntent(SearchIntent.ClearSearchHistory) },
                        onRemoveFromHistory = { viewModel.handleIntent(SearchIntent.RemoveFromHistory(it)) }
                    )
                }
                uiState.hasSearched && uiState.searchResults.isEmpty() -> {
                    EmptySearchScreen(query = uiState.query)
                }
                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.handleIntent(SearchIntent.PerformSearch) }
                    )
                }
                uiState.hasSearched -> {
                    SearchResultsContent(
                        query = uiState.query,
                        searchResults = uiState.searchResults,
                        selectedGenre = uiState.selectedGenre,
                        selectedPlatform = uiState.selectedPlatform,
                        favouriteIds = uiState.favouriteIds,
                        onGameClick = { viewModel.handleIntent(SearchIntent.GameClicked(it)) },
                        onFavouriteClick = { viewModel.handleIntent(SearchIntent.ToggleFavourite(it)) },
                        onGenreSelected = { viewModel.handleIntent(SearchIntent.SelectGenre(it)) },
                        onPlatformSelected = { viewModel.handleIntent(SearchIntent.SelectPlatform(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchInitialContent(
    suggestedGames: List<com.example.core.model.Game>,
    searchHistory: List<SearchHistoryItem>,
    favouriteIds: Set<Long>,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit,
    onRemoveFromHistory: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Search history section
        if (searchHistory.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextButton(onClick = onClearHistoryClick) {
                        Text("Clear")
                    }
                }
            }
            
            items(searchHistory) { historyItem ->
                RecentSearchItem(
                    query = historyItem.query,
                    resultCount = historyItem.resultCount,
                    onClick = { onRecentSearchClick(historyItem.query) },
                    onRemove = { onRemoveFromHistory(historyItem.query) }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        // Suggested games section
        if (suggestedGames.isNotEmpty()) {
            item {
                Text(
                    text = "Trending Games",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(
                        items = suggestedGames,
                        key = { it.id }
                    ) { game ->
                        GameCardHorizontal(
                            game = game,
                            onGameClick = onGameClick,
                            onFavouriteClick = onFavouriteClick,
                            isFavourited = favouriteIds.contains(game.id),
                            modifier = Modifier.fillParentMaxWidth(0.85f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentSearchItem(
    query: String,
    resultCount: Int,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.History,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = query,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (resultCount > 0) {
                Text(
                    text = "$resultCount results",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Remove",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SearchResultsContent(
    query: String,
    searchResults: List<com.example.core.model.Game>,
    selectedGenre: Genre?,
    selectedPlatform: Platform?,
    favouriteIds: Set<Long>,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    onGenreSelected: (Genre?) -> Unit,
    onPlatformSelected: (Platform?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Results count
        item {
            Text(
                text = "${searchResults.size} results for \"$query\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        
        // Genre filters
        item {
            Text(
                text = "Filter by Genre",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    GenreFilterChip(
                        genre = Genre.OTHER,
                        isSelected = selectedGenre == null,
                        onClick = { onGenreSelected(null) }
                    )
                }
                items(Genre.entries.toTypedArray()) { genre ->
                    GenreFilterChip(
                        genre = genre,
                        isSelected = selectedGenre == genre,
                        onClick = { onGenreSelected(genre) }
                    )
                }
            }
        }
        
        // Platform filters
        item {
            Text(
                text = "Filter by Platform",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    PlatformFilterChip(
                        platform = Platform.CROSSPLATFORM,
                        isSelected = selectedPlatform == null,
                        onClick = { onPlatformSelected(null) }
                    )
                }
                items(Platform.entries.toTypedArray()) { platform ->
                    PlatformFilterChip(
                        platform = platform,
                        isSelected = selectedPlatform == platform,
                        onClick = { onPlatformSelected(platform) }
                    )
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Search results
        items(
            items = searchResults,
            key = { it.id }
        ) { game ->
            GameCardHorizontal(
                game = game,
                onGameClick = onGameClick,
                onFavouriteClick = onFavouriteClick,
                isFavourited = favouriteIds.contains(game.id),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
    }
}