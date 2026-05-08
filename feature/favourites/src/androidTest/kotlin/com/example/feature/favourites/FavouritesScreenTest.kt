package com.example.feature.favourites

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import com.example.core.ui.theme.GameAppTheme
import io.mockk.coEvery
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
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var mockRepository: com.example.core.data.repository.UserRepository

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
        // Given loading state
        coEvery { mockRepository.getFavourites() } returns Result.Loading

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for loading
        composeTestRule.waitForIdle()

        // Then loading indicator is shown
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun `empty state is displayed when no favourites`() {
        // Given empty favourites
        coEvery { mockRepository.getFavourites() } returns Result.Success(emptyList())

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
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
        coEvery { mockRepository.getFavourites() } returns Result.Success(testGames)

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
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
    fun `error state shows retry button`() {
        // Given error state
        coEvery { mockRepository.getFavourites() } returns Result.Error("Failed to load favourites")

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for error
        composeTestRule.waitForIdle()

        // Then error screen with retry button is shown
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun `game click triggers navigation`() {
        // Given favourites data
        coEvery { mockRepository.getFavourites() } returns Result.Success(testGames)

        var navigatedGameId: Long? = null

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
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
        coEvery { mockRepository.getFavourites() } returns Result.Success(emptyList())

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
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
    fun `retry button reloads favourites`() {
        // First call returns error, second returns empty list
        var callCount = 0
        coEvery { mockRepository.getFavourites() } answers {
            callCount++
            if (callCount == 1) {
                Result.Error("Network error")
            } else {
                Result.Success(emptyList())
            }
        }

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Wait for error
        composeTestRule.waitForIdle()

        // Then error screen is shown
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()

        // When retry button is clicked
        composeTestRule.onNodeWithText("Retry").performClick()

        // Wait for reload
        composeTestRule.waitForIdle()

        // Then empty state is shown
        composeTestRule.onNodeWithText("No Favourites Yet").assertIsDisplayed()
    }

    @Test
    fun `multiple games are displayed correctly`() {
        // Given multiple favourites
        coEvery { mockRepository.getFavourites() } returns Result.Success(testGames)

        // When FavouritesScreen is displayed
        composeTestRule.setContent {
            Test23Theme {
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

        // And both are clickable
        var clickedGameId: Long? = null
        composeTestRule.setContent {
            Test23Theme {
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = { clickedGameId = it }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Cyberpunk 2077").performClick()
        assert(clickedGameId == 2L)
    }
}