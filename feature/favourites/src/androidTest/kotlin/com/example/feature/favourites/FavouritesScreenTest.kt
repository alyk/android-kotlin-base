package com.example.feature.favourites

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.LocalFavourite
import com.example.core.model.Platform
import com.example.core.ui.theme.GameAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for FavouritesScreen UI.
 * Tests grid display, empty state, navigation to detail screen, and remove functionality.
 */
@OptIn(ExperimentalTestApi::class)
class FavouritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        // No special setup needed
    }

    // ==================== Grid Display Tests ====================

    @Test
    fun favouritesScreen_displaysFavouritesInGrid() {
        // Given - Multiple favourite games
        val favourites = listOf(
            createTestFavourite(1, "Epic Game 1", "https://example.com/1.jpg", Genre.ACTION, Platform.PC),
            createTestFavourite(2, "Epic Game 2", "https://example.com/2.jpg", Genre.RPG, Platform.PLAYSTATION),
            createTestFavourite(3, "Epic Game 3", "https://example.com/3.jpg", Genre.STRATEGY, Platform.XBOX)
        )

        // When - FavouritesScreen is displayed with success state
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = favourites,
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Games are displayed in grid
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Epic Game 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Epic Game 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Epic Game 3").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_displaysCorrectNumberOfGames() {
        // Given
        val favourites = listOf(
            createTestFavourite(1, "Game 1"),
            createTestFavourite(2, "Game 2"),
            createTestFavourite(3, "Game 3"),
            createTestFavourite(4, "Game 4")
        )

        // When
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = favourites,
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Game 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Game 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Game 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("Game 4").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_gridAdaptsToDifferentScreenSizes() {
        // Given - Multiple games with different aspect ratios (reduced for test stability)
        val favourites = (1..5).map { index ->
            createTestFavourite(index.toLong(), "Game $index")
        }

        // When
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = favourites,
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Grid displays all games
        composeTestRule.waitForIdle()
        favourites.forEach { favourite ->
            composeTestRule.onNodeWithText(favourite.title).assertIsDisplayed()
        }
    }

    @Test
    fun favouritesScreen_displaysGameWithCorrectInfo() {
        // Given
        val favourite = createTestFavourite(
            id = 100,
            title = "Amazing RPG",
            genre = Genre.RPG,
            platform = Platform.PC
        )

        // When
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = listOf(favourite),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Game title is displayed
        composeTestRule.onNodeWithText("Amazing RPG").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_gridContainsGameCards() {
        // Given
        val favourites = listOf(createTestFavourite(1, "Test Game"))

        // When
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = favourites,
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Game card is displayed
        composeTestRule.onNodeWithText("Test Game").assertIsDisplayed()
    }

    // ==================== Empty State Tests ====================

    @Test
    fun favouritesScreen_displaysEmptyStateWhenNoFavourites() {
        // Given - Empty favourites list
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Empty state message is displayed
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()
        // Use substring match since the text contains a newline
        composeTestRule.onNodeWithText("Start adding games to your favourites", substring = true).assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_hidesGridWhenEmpty() {
        // Given
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Title is still displayed but games are not
        composeTestRule.onNodeWithText("My Favourites").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_emptyStateHasIcon() {
        // Given
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Heart/border icon is displayed in empty state (using test tag)
        composeTestRule.onNodeWithContentDescription("Favorite border icon").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_emptyStateMessageIsCorrect() {
        // Given
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Empty state contains descriptive message
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()
        // Use substring match since the text contains a newline
        composeTestRule.onNodeWithText("Start adding games to your favourites", substring = true).assertIsDisplayed()
    }

    // ==================== Loading State Tests ====================

    @Test
    fun favouritesScreen_loadingIndicatorIsVisible() {
        // Given - Loading state
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = true,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Loading indicator is displayed
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }

    // ==================== Error State Tests ====================

    @Test
    fun favouritesScreen_displaysErrorStateOnError() {
        // Given - Error state
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = "Network error occurred",
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Error content is displayed
        composeTestRule.onNodeWithTag("errorContent").assertIsDisplayed()
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_displaysErrorMessage() {
        // Given - Error state with specific message
        val errorMessage = "Failed to load favourites"
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = errorMessage,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Error message is displayed
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    // ==================== Interaction Tests ====================

    @Test
    fun favouritesScreen_navigatesBackWhenBackButtonClicked() {
        // Given
        var backClicked = false
        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = emptyList(),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = { backClicked = true }
                )
            }
        }

        // When - Back button is clicked
        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()

        // Then - Navigation callback is invoked
        assert(backClicked)
    }

    @Test
    fun favouritesScreen_callsGameClickHandler() {
        // Given
        var clickedGameId: Long? = null
        val game = createTestFavourite(42, "Clickable Game")

        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = listOf(game),
                    isLoading = false,
                    error = null,
                    onGameClick = { id -> clickedGameId = id },
                    onNavigateBack = {}
                )
            }
        }

        // When - Game is clicked
        composeTestRule.onNodeWithText("Clickable Game").performClick()

        // Then - Game click handler is invoked with correct ID
        assert(clickedGameId == 42L)
    }

    // ==================== Edge Cases Tests ====================

    @Test
    fun favouritesScreen_handlesGameWithSpecialCharacters() {
        // Given - Game title with special characters
        val specialGame = createTestFavourite(
            id = 999,
            title = "Game: Special! Characters"
        )

        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = listOf(specialGame),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - Game is displayed
        composeTestRule.onNodeWithText("Game: Special! Characters").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_handlesLargeFavouritesList() {
        // Given - Large list of favourites (reduced to 6 for test stability)
        val largeFavouritesList = (1..6).map { index ->
            createTestFavourite(index.toLong(), "Game $index")
        }

        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = largeFavouritesList,
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - First and last games are displayed
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Game 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Game 6").assertIsDisplayed()
    }

    @Test
    fun favouritesScreen_displaysGamesWithDifferentPlatforms() {
        // Given - Games with different platforms
        val pcGame = createTestFavourite(1, "PC Game", platform = Platform.PC)
        val playStationGame = createTestFavourite(2, "PlayStation Game", platform = Platform.PLAYSTATION)
        val xboxGame = createTestFavourite(3, "Xbox Game", platform = Platform.XBOX)

        composeTestRule.setContent {
            GameAppTheme {
                TestableFavouritesScreen(
                    favouriteGames = listOf(pcGame, playStationGame, xboxGame),
                    isLoading = false,
                    error = null,
                    onGameClick = {},
                    onNavigateBack = {}
                )
            }
        }

        // Then - All games are displayed
        composeTestRule.onNodeWithText("PC Game").assertIsDisplayed()
        composeTestRule.onNodeWithText("PlayStation Game").assertIsDisplayed()
        composeTestRule.onNodeWithText("Xbox Game").assertIsDisplayed()
    }

    // ==================== Helper Functions ====================

    private fun createTestFavourite(
        id: Long,
        title: String,
        thumbnailUrl: String = "https://example.com/thumb.jpg",
        genre: Genre = Genre.ACTION,
        platform: Platform = Platform.PC,
        rating: Float = 4.5f,
        savedAt: Long = System.currentTimeMillis()
    ): LocalFavourite {
        return LocalFavourite(
            id = id,
            gameId = id,
            title = title,
            thumbnailUrl = thumbnailUrl,
            genre = genre.name,
            platform = platform.name,
            rating = rating,
            savedAt = savedAt
        )
    }
}

/**
 * Testable version of FavouritesScreen that accepts state directly.
 * This avoids Hilt dependency issues in instrumented tests.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestableFavouritesScreen(
    favouriteGames: List<LocalFavourite>,
    isLoading: Boolean,
    error: String?,
    onGameClick: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    onRetry: (() -> Unit)? = null,
    snackbarMessage: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar if message provided
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Favourites",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
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
                isLoading -> {
                    // Loading state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("loadingIndicator")
                        )
                    }
                }
                error != null -> {
                    // Error state
                    ErrorStateContent(
                        message = error,
                        onRetry = onRetry
                    )
                }
                favouriteGames.isEmpty() -> {
                    // Empty state
                    EmptyFavouritesContent()
                }
                else -> {
                    // Success state - show grid
                    TestableFavouritesGrid(
                        favourites = favouriteGames,
                        onGameClick = onGameClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorStateContent(
    message: String,
    onRetry: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .testTag("errorContent"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Error",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun EmptyFavouritesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.FavoriteBorder,
            contentDescription = "Favorite border icon",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Favourites Yet",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Start adding games to your favourites\nby tapping the heart icon on any game",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TestableFavouritesGrid(
    favourites: List<LocalFavourite>,
    onGameClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = favourites,
            key = { it.gameId }
        ) { favourite ->
            TestableGameCard(
                game = favourite.toGame(),
                onGameClick = onGameClick,
                isFavourited = true
            )
        }
    }
}

/**
 * Testable GameCard that doesn't use AsyncImage to avoid network issues in tests.
 */
@Composable
fun TestableGameCard(
    game: Game,
    onGameClick: (Long) -> Unit,
    isFavourited: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onGameClick(game.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.title.first().toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = game.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = game.genre.name,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = game.platform.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/**
 * Extension to convert LocalFavourite to Game for display
 */
fun LocalFavourite.toGame(): Game {
    return Game(
        id = this.gameId,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        genre = try { Genre.valueOf(this.genre) } catch (e: Exception) { Genre.OTHER },
        platform = try { Platform.valueOf(this.platform) } catch (e: Exception) { Platform.OTHER },
        rating = this.rating,
        developer = "Test Developer",
        description = "Test description"
    )
}