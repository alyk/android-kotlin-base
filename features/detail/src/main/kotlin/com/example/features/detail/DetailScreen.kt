package com.example.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.model.Game
import com.example.core.model.Screenshot
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.GameCardHorizontal
import com.example.core.ui.components.GenreChip
import com.example.core.ui.components.LoadingScreen
import com.example.core.ui.components.PlatformBadge
import com.example.core.ui.components.RatingText
import com.example.core.ui.components.getGenreColor
import kotlinx.coroutines.flow.collectLatest
import org.jsoup.Jsoup

/**
 * Game Detail screen composable displaying full game information.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Long,
    viewModel: DetailViewModel,
    onNavigateBack: () -> Unit,
    onGameClick: (Long) -> Unit,
    onOpenUrl: (String) -> Unit,
    onShareGame: (Game) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    
    // Load game detail on first composition
    LaunchedEffect(gameId) {
        viewModel.handleIntent(DetailIntent.LoadGameDetail(gameId))
    }
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is DetailEffect.NavigateToGameDetail -> {
                    onGameClick(effect.gameId)
                }
                is DetailEffect.NavigateBack -> {
                    onNavigateBack()
                }
                is DetailEffect.OpenUrl -> {
                    onOpenUrl(effect.url)
                }
                is DetailEffect.ShareGameInfo -> {
                    onShareGame(effect.game)
                }
                is DetailEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is DetailEffect.ShowFavouriteAdded -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} added to favourites")
                }
                is DetailEffect.ShowFavouriteRemoved -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} removed from favourites")
                }
                is DetailEffect.ShowAddedToLibrary -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} added to library")
                }
                is DetailEffect.ShowRemovedFromLibrary -> {
                    snackbarHostState.showSnackbar("${effect.gameTitle} removed from library")
                }
            }
        }
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.handleIntent(DetailIntent.ShareGame) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share"
                        )
                    }
                    IconButton(
                        onClick = { viewModel.handleIntent(DetailIntent.ToggleFavourite) }
                    ) {
                        Icon(
                            imageVector = if (uiState.isFavourited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (uiState.isFavourited) "Remove from favourites" else "Add to favourites",
                            tint = if (uiState.isFavourited) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            uiState.error != null -> {
                ErrorScreen(
                    message = uiState.error ?: "Unknown error",
                    onRetry = { viewModel.handleIntent(DetailIntent.RefreshDetail) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            uiState.game != null -> {
                DetailContent(
                    game = uiState.game!!,
                    screenshots = uiState.screenshots,
                    similarGames = uiState.similarGames,
                    isInLibrary = uiState.isInLibrary,
                    onGameClick = { viewModel.handleIntent(DetailIntent.GameClicked(it)) },
                    onOpenWebsite = { viewModel.handleIntent(DetailIntent.OpenWebsite) },
                    onAddToLibrary = { viewModel.handleIntent(DetailIntent.AddToLibrary) },
                    onRemoveFromLibrary = { viewModel.handleIntent(DetailIntent.RemoveFromLibrary) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    game: Game,
    screenshots: List<Screenshot>,
    similarGames: List<Game>,
    isInLibrary: Boolean,
    onGameClick: (Long) -> Unit,
    onOpenWebsite: () -> Unit,
    onAddToLibrary: () -> Unit,
    onRemoveFromLibrary: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero image with gradient
        item {
            HeroImageSection(game = game)
        }
        
        // Game title and basic info
        item {
            GameInfoSection(
                game = game,
                isInLibrary = isInLibrary,
                onOpenWebsite = onOpenWebsite,
                onAddToLibrary = if (isInLibrary) onRemoveFromLibrary else onAddToLibrary,
                isLibraryActionAdd = !isInLibrary
            )
        }
        
        // Description
        item {
            DescriptionSection(description = game.description)
        }
        
        // Screenshots
        if (screenshots.isNotEmpty()) {
            item {
                Text(
                    text = "Screenshots",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            
            item {
                ScreenshotsRow(screenshots = screenshots)
            }
        }
        
        // Similar games
        if (similarGames.isNotEmpty()) {
            item {
                Text(
                    text = "Similar Games",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = similarGames,
                        key = { it.id }
                    ) { similarGame ->
                        GameCardHorizontal(
                            game = similarGame,
                            onGameClick = onGameClick,
                            modifier = Modifier.fillParentMaxWidth(0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroImageSection(game: Game) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    ) {
        AsyncImage(
            model = game.thumbnailUrl,
            contentDescription = game.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        // Title overlay at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GenreChip(genre = game.genre)
                PlatformBadge(platform = game.platform)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = game.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun GameInfoSection(
    game: Game,
    isInLibrary: Boolean,
    onOpenWebsite: () -> Unit,
    onAddToLibrary: () -> Unit,
    isLibraryActionAdd: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Rating and release info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingText(rating = game.rating)
            
            Text(
                text = "Released: ${game.releaseDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Developer and publisher
        Text(
            text = "By ${game.developer}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        if (game.publisher.isNotBlank() && game.publisher != game.developer) {
            Text(
                text = "Published by ${game.publisher}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onOpenWebsite,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.OpenInBrowser,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Visit Website")
            }
            
            OutlinedButton(
                onClick = onAddToLibrary,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isLibraryActionAdd) Icons.Filled.Add else Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isLibraryActionAdd) "Add to Library" else "In Library")
            }
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Parse HTML and remove tags
        val cleanDescription = remember(description) {
            Jsoup.parse(description).text()
        }
        
        Text(
            text = cleanDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ScreenshotsRow(screenshots: List<Screenshot>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = screenshots,
            key = { it.id }
        ) { screenshot ->
            AsyncImage(
                model = screenshot.imageUrl,
                contentDescription = "Screenshot",
                modifier = Modifier
                    .width(280.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}