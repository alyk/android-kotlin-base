package com.example.features.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.model.UserGame
import com.example.core.ui.components.EmptyListScreen
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.FavouriteGameCard
import kotlinx.coroutines.flow.collectLatest

/**
 * Favourites screen composable displaying user's favourite games.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel,
    onGameClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is FavouritesEffect.NavigateToGameDetail -> {
                    onGameClick(effect.gameId)
                }
                is FavouritesEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is FavouritesEffect.ShowFavouriteRemoved -> {
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
                        text = "Favourites",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingState()
                }
                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.handleIntent(FavouritesIntent.LoadFavourites) }
                    )
                }
                uiState.isEmpty -> {
                    EmptyFavouritesState()
                }
                else -> {
                    FavouritesList(
                        favourites = uiState.favouriteGames,
                        onGameClick = { viewModel.handleIntent(FavouritesIntent.GameClicked(it)) },
                        onRemoveFavourite = { viewModel.handleIntent(FavouritesIntent.RemoveFavourite(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading favourites...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyFavouritesState() {
    EmptyListScreen(
        title = "No Favourites Yet",
        message = "Start adding games to your favourites by tapping the heart icon on any game.",
        icon = Icons.Filled.FavoriteBorder
    )
}

@Composable
private fun FavouritesList(
    favourites: List<UserGame>,
    onGameClick: (Long) -> Unit,
    onRemoveFavourite: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "${favourites.size} favourite${if (favourites.size != 1) "s" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(
            items = favourites,
            key = { it.gameId }
        ) { favourite ->
            FavouriteGameCard(
                userGame = favourite,
                onGameClick = { onGameClick(favourite.gameId) },
                onRemoveClick = { onRemoveFavourite(favourite.gameId) }
            )
        }
    }
}