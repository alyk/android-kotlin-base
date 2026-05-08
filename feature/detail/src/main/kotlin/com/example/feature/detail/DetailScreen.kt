package com.example.feature.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.GenreChip
import com.example.core.ui.components.LoadingScreen
import com.example.core.ui.components.RatingText
import com.example.core.ui.components.displayName
import kotlinx.coroutines.flow.collectLatest

/**
 * Game Detail screen composable.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    gameId: Long,
    onNavigateBack: () -> Unit,
    onShowScreenshot: (String) -> Unit,
    onOpenUrl: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Load game on first composition
    LaunchedEffect(gameId) {
        viewModel.handleIntent(GameDetailIntent.LoadGame(gameId))
    }
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is GameDetailEffect.ShowScreenshot -> onShowScreenshot(effect.url)
                is GameDetailEffect.OpenUrl -> onOpenUrl(effect.url)
                is GameDetailEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
                is GameDetailEffect.NavigateBack -> onNavigateBack()
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
                    uiState.game?.let { game ->
                        IconButton(
                            onClick = { viewModel.handleIntent(GameDetailIntent.ToggleFavourite) }
                        ) {
                            Icon(
                                imageVector = if (uiState.isFavourite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Filled.FavoriteBorder
                                },
                                contentDescription = if (uiState.isFavourite) {
                                    "Remove from favourites"
                                } else {
                                    "Add to favourites"
                                },
                                tint = if (uiState.isFavourite) Color.Red else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        if (game.thumbnailUrl.isNotBlank()) {
                            IconButton(
                                onClick = { viewModel.handleIntent(GameDetailIntent.VisitWebsite) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Public,
                                    contentDescription = "Visit website"
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            if (uiState.game?.thumbnailUrl?.isNotBlank() == true) {
                FloatingActionButton(
                    onClick = { viewModel.handleIntent(GameDetailIntent.VisitWebsite) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Public,
                        contentDescription = "Visit Website"
                    )
                }
            }
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
                    LoadingScreen()
                }
                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.handleIntent(GameDetailIntent.RefreshGame) }
                    )
                }
                uiState.game != null -> {
                    GameDetailContent(
                        game = uiState.game!!,
                        screenshots = uiState.screenshots,
                        minimumRequirements = uiState.minimumRequirements,
                        recommendedRequirements = uiState.recommendedRequirements,
                        onScreenshotClick = { viewModel.handleIntent(GameDetailIntent.GameClicked(it)) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GameDetailContent(
    game: Game,
    screenshots: List<String>,
    minimumRequirements: String,
    recommendedRequirements: String,
    onScreenshotClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Hero image with pager
        item {
            val pagerState = rememberPagerState(pageCount = { 
                if (screenshots.isNotEmpty()) screenshots.size else 1 
            })
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                if (screenshots.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        AsyncImage(
                            model = screenshots[page],
                            contentDescription = "Screenshot ${page + 1}",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { onScreenshotClick(screenshots[page]) },
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    // Page indicator
                    if (screenshots.size > 1) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(screenshots.size) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(if (isSelected) 8.dp else 6.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                                        )
                                )
                            }
                        }
                    }
                } else if (game.thumbnailUrl.isNotBlank()) {
                    AsyncImage(
                        model = game.thumbnailUrl,
                        contentDescription = game.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Image",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )
                
                // Title overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingText(rating = game.rating)
                        
                        Text(
                            text = "•",
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        
                        Text(
                            text = game.releaseDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
        
        // Game info
        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GenreChip(genre = game.genre)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Platform chip
                PlatformChip(platform = game.platform)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Description
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Game details card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Game Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    GameDetailRow(
                        label = "Genre",
                        value = game.genre.displayName()
                    )
                    
                    GameDetailRow(
                        label = "Platform",
                        value = game.platform.displayName()
                    )
                    
                    GameDetailRow(
                        label = "Release Date",
                        value = game.releaseDate
                    )
                    
                    GameDetailRow(
                        label = "Publisher",
                        value = game.publisher
                    )
                    
                    GameDetailRow(
                        label = "Developer",
                        value = game.developer
                    )
                }
            }
        }
        
        // System Requirements
        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "System Requirements",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                if (minimumRequirements.isNotBlank()) {
                    SystemRequirementsCard(
                        title = "Minimum",
                        requirements = minimumRequirements
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                if (recommendedRequirements.isNotBlank()) {
                    SystemRequirementsCard(
                        title = "Recommended",
                        requirements = recommendedRequirements
                    )
                }
            }
        }
        
        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PlatformChip(
    platform: Platform
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(
            text = platform.displayName(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun GameDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SystemRequirementsCard(
    title: String,
    requirements: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = requirements,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}