package com.example.feature.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.ui.theme.GameAppTheme
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for SearchScreen composable.
 * Tests search functionality, state management, and user interactions.
 */
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: SearchViewModel
    private lateinit var mockRepository: SearchRepository

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
        mockRepository = mockk()
        viewModel = SearchViewModel(mockRepository)
    }

    @Test
    fun `initial state shows search placeholder`() {
        // Given initial state with empty query
        every { mockRepository.searchGames(any()) } returns Result.Success(emptyList())

        // When SearchScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Then search placeholder is shown
        composeTestRule.onNodeWithText("Find Your Next Game").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search for games by title, genre, or platform").assertIsDisplayed()
    }

    @Test
    fun `search text field is displayed in top bar`() {
        // When SearchScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Then search text field is visible
        composeTestRule.onNodeWithText("Search games...").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
    }

    @Test
    fun `typing in search field updates query`() {
        // Given empty initial state
        every { mockRepository.searchGames(any()) } returns Result.Success(emptyList())

        // When SearchScreen is displayed and text is typed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Search games...").performTextInput("elden")

        // Then query is updated
        composeTestRule.onNodeWithText("Search games...").assertTextContains("elden")
    }

    @Test
    fun `search results are displayed when found`() {
        // Given search returns results
        every { mockRepository.searchGames("elden") } returns Result.Success(
            testGames.filter { it.title.contains("Elden", ignoreCase = true) }
        )

        // When SearchScreen is displayed with query
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Type search query
        composeTestRule.onNodeWithText("Search games...").performTextInput("elden")

        // Wait for results
        composeTestRule.waitForIdle()

        // Then search results are shown
        composeTestRule.onNodeWithText("1 result").assertIsDisplayed()
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
    }

    @Test
    fun `empty search shows empty state`() {
        // Given search returns no results
        every { mockRepository.searchGames("xyz123") } returns Result.Success(emptyList())

        // When SearchScreen is displayed with query that has no results
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Type search query with no results
        composeTestRule.onNodeWithText("Search games...").performTextInput("xyz123")

        // Wait for results
        composeTestRule.waitForIdle()

        // Then empty search screen is shown
        composeTestRule.onNodeWithText("No Results Found").assertIsDisplayed()
    }

    @Test
    fun `error state shows error screen`() {
        // Given search returns an error
        every { mockRepository.searchGames(any()) } returns Result.Error("Network error")

        // When SearchScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Type search query
        composeTestRule.onNodeWithText("Search games...").performTextInput("test")

        // Wait for error
        composeTestRule.waitForIdle()

        // Then error screen is shown
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Network error").assertIsDisplayed()
    }

    @Test
    fun `clear button removes search text`() {
        // Given search with results
        every { mockRepository.searchGames(any()) } returns Result.Success(testGames)

        // When SearchScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = {}
                )
            }
        }

        // Type search query
        composeTestRule.onNodeWithText("Search games...").performTextInput("elden")

        // Wait for results
        composeTestRule.waitForIdle()

        // Click clear button
        composeTestRule.onNodeWithContentDescription("Clear").performClick()

        // Wait for clearing
        composeTestRule.waitForIdle()

        // Then search placeholder is shown again
        composeTestRule.onNodeWithText("Find Your Next Game").assertIsDisplayed()
    }

    @Test
    fun `game click triggers navigation effect`() {
        // Given search returns results
        every { mockRepository.searchGames("elden") } returns Result.Success(
            testGames.filter { it.title.contains("Elden", ignoreCase = true) }
        )

        var navigatedGameId: Long? = null

        // When SearchScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = { gameId ->
                        navigatedGameId = gameId
                    }
                )
            }
        }

        // Type search query
        composeTestRule.onNodeWithText("Search games...").performTextInput("elden")

        // Wait for results
        composeTestRule.waitForIdle()

        // Click on game
        composeTestRule.onNodeWithText("Elden Ring").performClick()

        // Then navigation is triggered
        assert(navigatedGameId == 1L)
    }
}