package com.example.feature.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.favourites.R
import com.example.core.ui.components.EmptyListScreen
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.LocalFavouriteCard
import kotlinx.coroutines.flow.collectLatest
import com.example.core.model.LocalFavourite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onNavigateToGameDetail: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle side effects
    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is FavouritesEffect.NavigateToGameDetail -> {
                    onNavigateToGameDetail(effect.gameId)
                }
                is FavouritesEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is FavouritesEffect.ShowFavouriteRemoved -> {
                    snackbarHostState.showSnackbar(
                        message = "${effect.gameTitle} removed from favourites"
                    )
                }
            }
        }
    }
    
    // Load favourites on first composition
    LaunchedEffect(Unit) {
        viewModel.handleIntent(FavouritesIntent.LoadFavourites)
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favourites_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = stringResource(R.string.loading_favourites),
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.handleIntent(FavouritesIntent.RefreshFavourites) },
                        
                    )
                }
                
                uiState.isEmpty -> {
                    EmptyListScreen(
                        icon = Icons.Default.FavoriteBorder,
                        title = stringResource(R.string.no_favourites_title),
                        message = stringResource(R.string.no_favourites_description)
                    )
                }
                
                else -> {
                    FavouritesList(
                        favouriteGames = uiState.favouriteGames,
                        onGameClick = { gameId ->
                            viewModel.handleIntent(FavouritesIntent.GameClicked(gameId))
                        },
                        onRemoveFavourite = { gameId ->
                            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(gameId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavouritesList(
    favouriteGames: List<LocalFavourite>,
    onGameClick: (Long) -> Unit,
    onRemoveFavourite: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favouriteGames, key = { it.id }) { favourite ->
            LocalFavouriteCard(
                favourite = favourite,
                onGameClick = { onGameClick(favourite.gameId) },
                onRemoveClick = { onRemoveFavourite(favourite.gameId) }
            )
        }
    }
}
