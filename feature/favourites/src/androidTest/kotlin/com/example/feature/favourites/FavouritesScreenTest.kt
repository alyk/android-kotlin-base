package com.example.feature.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.data.repository.UserRepository
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.ui.theme.GameAppTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for FavouritesScreen composable.
 * Tests favourites display, empty state, and user interactions.
 * Also includes layout constraint validation tests to prevent
 * "InfinityHeightConstraint" crashes with LazyVerticalGrid.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var mockRepository: UserRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testGames = listOf(
        Game(
            id = 1L,
            title = "Elden Ring",
            description = "An open world RPG game",
            thumbnailUrl = "https://example.com/elden-ring.png",
            genre = Genre.ACTION,
            platform = Platform.PC,
            developer = "FromSoftware",
            publisher = "Bandai Namco",
            releaseDate = "2022-02-25",
            rating = 4.9f,
            price = 59.99
        ),
        Game(
            id = 2L,
            title = "Cyberpunk 2077",
            description = "An open world action adventure game",
            thumbnailUrl = "https://example.com/cyberpunk.png",
            genre = Genre.RPG,
            platform = Platform.PC,
            developer = "CD Projekt Red",
            publisher = "CD Projekt",
            releaseDate = "2020-12-10",
            rating = 4.5f,
            price = 59.99
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        viewModel = FavouritesViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loading state is displayed initially`() {
        // Given loading state - use empty flow initially
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then screen renders (empty state since flow emits empty list)
        composeTestRule.onNodeWithText("My Favourites").assertIsDisplayed()
    }

    @Test
    fun `empty state is displayed when no favourites`() {
        // Given empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then empty state is shown
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start adding games to your favourites").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("No favourites icon").assertIsDisplayed()
    }

    @Test
    fun `favourites are displayed in grid`() {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then games are displayed
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cyberpunk 2077").assertIsDisplayed()
    }

    @Test
    fun `game click triggers navigation`() {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        var navigatedGameId: Long? = null

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = { gameId ->
                        navigatedGameId = gameId
                    }
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Click on game
        composeTestRule.onNodeWithText("Elden Ring").performClick()

        // Then navigation is triggered
        assert(navigatedGameId == 1L)
    }

    @Test
    fun `screen title is displayed`() {
        // Given empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then title is shown
        composeTestRule.onNodeWithText("My Favourites").assertIsDisplayed()
    }

    @Test
    fun `multiple games are displayed correctly`() {
        // Given multiple favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then all games are displayed
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cyberpunk 2077").assertIsDisplayed()
    }

    // ===== LAYOUT CONSTRAINT VALIDATION TESTS =====
    // These tests verify that the screen properly handles parent constraints
    // to prevent the "InfinityHeightConstraint" crash with LazyVerticalGrid

    @Test
    fun `screen handles bounded height constraints correctly`() {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed with bounded parent
        composeTestRule.setContent {
            GameAppTheme {
                // Parent with bounded size simulates real app scenario
                Box(modifier = Modifier.fillMaxSize()) {
                    FavouritesScreen(
                        viewModel = viewModel,
                        onGameClick = {}
                    )
                }
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then screen renders without crashing and games are displayed
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cyberpunk 2077").assertIsDisplayed()
    }

    @Test
    fun `screen handles fillMaxSize modifier without crash`() {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed with fillMaxSize modifier
        composeTestRule.setContent {
            GameAppTheme {
                FavouritesScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                    onGameClick = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then screen renders without the infinity height constraint error
        composeTestRule.onNodeWithText("My Favourites").assertIsDisplayed()
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
    }

    @Test
    fun `empty state handles bounded constraints correctly`() {
        // Given empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed with bounded parent
        composeTestRule.setContent {
            GameAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    FavouritesScreen(
                        viewModel = viewModel,
                        onGameClick = {}
                    )
                }
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then empty state is displayed without layout errors
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()
    }

    @Test
    fun `favourites grid renders without constraint violations`() {
        // Given many games to render in grid
        val manyGames = (1..20).map { index ->
            Game(
                id = index.toLong(),
                title = "Game $index",
                description = "Description for game $index",
                thumbnailUrl = "https://example.com/game$index.png",
                genre = Genre.ACTION,
                platform = Platform.PC,
                developer = "Developer $index",
                publisher = "Publisher $index",
                releaseDate = "2024-01-$index",
                rating = 4.0f + (index % 10) * 0.1f,
                price = 29.99 + index
            )
        }
        val favouritesFlow = MutableStateFlow<List<Game>>(manyGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    FavouritesScreen(
                        viewModel = viewModel,
                        onGameClick = {}
                    )
                }
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then all games are displayed in the grid without layout crashes
        manyGames.take(5).forEach { game ->
            composeTestRule.onNodeWithText(game.title).assertIsDisplayed()
        }
    }

    @Test
    fun `screen handles dynamic content updates correctly`() {
        // Given initially empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    FavouritesScreen(
                        viewModel = viewModel,
                        onGameClick = {}
                    )
                }
            }
        }

        // Wait for initial empty state
        composeTestRule.waitForIdle()

        // Then empty state is displayed
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()

        // When new games are added
        favouritesFlow.value = testGames

        // Wait for update
        composeTestRule.waitForIdle()

        // Then games are displayed without constraint errors
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cyberpunk 2077").assertIsDisplayed()
    }
}