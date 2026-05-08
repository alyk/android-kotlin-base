package com.example.features.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.GameCard
import com.example.core.ui.components.GenreFilterChip
import com.example.core.ui.components.LoadingScreen
import com.example.core.ui.components.PlatformFilterChip
import kotlinx.coroutines.flow.collectLatest

/**
 * Discover screen composable that displays featured, popular, and recently added games.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    onGameClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is DiscoverEffect.NavigateToGameDetail -> {
                    onGameClick(effect.gameId)
                }
                is DiscoverEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is DiscoverEffect.ShowFavouriteAdded -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} added to favourites")
                }
                is DiscoverEffect.ShowFavouriteRemoved -> {
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
                        text = "Discover",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.handleIntent(DiscoverIntent.RefreshData) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isLoading,
            onRefresh = { viewModel.handleIntent(DiscoverIntent.RefreshData) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.featuredGames.isEmpty() -> {
                    LoadingScreen()
                }
                uiState.error != null && uiState.featuredGames.isEmpty() -> {
                    ErrorScreen(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.handleIntent(DiscoverIntent.LoadInitialData) }
                    )
                }
                else -> {
                    DiscoverContent(
                        uiState = uiState,
                        onGameClick = { viewModel.handleIntent(DiscoverIntent.GameClicked(it)) },
                        onFavouriteClick = { viewModel.handleIntent(DiscoverIntent.ToggleFavourite(it)) },
                        onGenreSelected = { viewModel.handleIntent(DiscoverIntent.SelectGenre(it)) },
                        onPlatformSelected = { viewModel.handleIntent(DiscoverIntent.SelectPlatform(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DiscoverContent(
    uiState: DiscoverUiState,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    onGenreSelected: (Genre?) -> Unit,
    onPlatformSelected: (Platform?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Genre filters
        item {
            GenreFilterSection(
                selectedGenre = uiState.selectedGenre,
                onGenreSelected = onGenreSelected
            )
        }
        
        // Platform filters
        item {
            PlatformFilterSection(
                selectedPlatform = uiState.selectedPlatform,
                onPlatformSelected = onPlatformSelected
            )
        }
        
        // Featured section
        if (uiState.featuredGames.isNotEmpty()) {
            item {
                SectionHeader(title = "Featured Games")
            }
            
            item {
                FeaturedGamesRow(
                    games = uiState.featuredGames,
                    favouriteIds = uiState.favouriteIds,
                    onGameClick = onGameClick,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }
        
        // Popular section
        if (uiState.popularGames.isNotEmpty()) {
            item {
                SectionHeader(title = "Popular Games")
            }
            
            items(
                items = uiState.popularGames,
                key = { it.id }
            ) { game ->
                GameListItem(
                    game = game,
                    isFavourited = uiState.favouriteIds.contains(game.id),
                    onGameClick = onGameClick,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }
        
        // Recently Added section
        if (uiState.recentlyAdded.isNotEmpty()) {
            item {
                SectionHeader(title = "Recently Added")
            }
            
            item {
                RecentlyAddedRow(
                    games = uiState.recentlyAdded,
                    favouriteIds = uiState.favouriteIds,
                    onGameClick = onGameClick,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }
    }
}

@Composable
private fun GenreFilterSection(
    selectedGenre: Genre?,
    onGenreSelected: (Genre?) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // All filter
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
}

@Composable
private fun PlatformFilterSection(
    selectedPlatform: Platform?,
    onPlatformSelected: (Platform?) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // All filter
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
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun FeaturedGamesRow(
    games: List<com.example.core.model.Game>,
    favouriteIds: Set<Long>,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = games,
            key = { it.id }
        ) { game ->
            GameCard(
                game = game,
                onGameClick = onGameClick,
                onFavouriteClick = onFavouriteClick,
                isFavourited = favouriteIds.contains(game.id),
                modifier = Modifier.fillParentMaxWidth(0.85f)
            )
        }
    }
}

@Composable
private fun GameListItem(
    game: com.example.core.model.Game,
    isFavourited: Boolean,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit
) {
    com.example.core.ui.components.GameCardHorizontal(
        game = game,
        onGameClick = onGameClick,
        onFavouriteClick = onFavouriteClick,
        isFavourited = isFavourited,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    )
}

@Composable
private fun RecentlyAddedRow(
    games: List<com.example.core.model.Game>,
    favouriteIds: Set<Long>,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = games,
            key = { it.id }
        ) { game ->
            GameCardHorizontalCompact(
                game = game,
                isFavourited = favouriteIds.contains(game.id),
                onGameClick = onGameClick,
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}

@Composable
private fun GameCardHorizontalCompact(
    game: com.example.core.model.Game,
    isFavourited: Boolean,
    onGameClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit
) {
    com.example.core.ui.components.GameCardHorizontal(
        game = game,
        onGameClick = onGameClick,
        onFavouriteClick = onFavouriteClick,
        isFavourited = isFavourited,
        modifier = Modifier.fillParentMaxWidth(0.7f)
    )
}