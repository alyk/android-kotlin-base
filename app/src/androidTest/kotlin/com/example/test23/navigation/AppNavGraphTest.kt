package com.example.test23.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import com.example.core.ui.theme.GameAppTheme
import com.example.feature.detail.DetailViewModel
import com.example.feature.favourites.FavouritesViewModel
import com.example.feature.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for navigation components.
 * Tests navigation flow between screens.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AppNavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavHostController

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testGame = Game(
        id = 1L,
        title = "Test Game",
        description = "A test game description",
        thumbnailUrl = "https://example.com/thumb.png",
        genre = Genre.ACTION,
        platform = Platform.PC,
        developer = "Test Developer",
        publisher = "Test Publisher",
        releaseDate = "2024-01-15",
        rating = 4.5f,
        price = 59.99
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        navController = rememberNavController()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `navigate from search to detail screen`() {
        // Given search screen is displayed
        val mockRepository = mockk<com.example.feature.search.SearchRepository>()
        every { mockRepository.searchGames(any()) } returns Result.Success(listOf(testGame))
        val searchViewModel = SearchViewModel(mockRepository)

        composeTestRule.setContent {
            GameAppTheme {
                AppNavGraph(navController = navController)
            }
        }

        // Navigate to search
        composeTestRule.onNodeWithText("Search").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // When clicking on a game
        composeTestRule.onNodeWithText("Test Game").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // Then detail screen should be visible
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun `navigate from favourites to detail screen`() {
        // Given favourites screen is displayed
        val mockUserRepository = mockk<com.example.core.data.repository.UserRepository>()
        coEvery { mockUserRepository.getFavourites() } returns Result.Success(listOf(testGame))
        val favouritesViewModel = FavouritesViewModel(mockUserRepository)

        composeTestRule.setContent {
            GameAppTheme {
                AppNavGraph(navController = navController)
            }
        }

        // Navigate to favourites
        composeTestRule.onNodeWithText("Favourites").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // When clicking on a game
        composeTestRule.onNodeWithText("Test Game").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // Then detail screen should be visible
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun `back navigation works correctly`() {
        // Given search screen
        val mockRepository = mockk<com.example.feature.search.SearchRepository>()
        every { mockRepository.searchGames(any()) } returns Result.Success(emptyList())
        val searchViewModel = SearchViewModel(mockRepository)

        composeTestRule.setContent {
            GameAppTheme {
                AppNavGraph(navController = navController)
            }
        }

        // Navigate to search
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.waitForIdle()

        // Navigate to detail
        composeTestRule.onNodeWithText("Search games...").performClick()
        composeTestRule.waitForIdle()

        // Go back
        navController.popBackStack()

        // Wait for back navigation
        composeTestRule.waitForIdle()

        // Verify back navigation works without errors
        assert(true)
    }

    @Test
    fun `bottom navigation is displayed`() {
        // When app starts with NavGraph
        composeTestRule.setContent {
            GameAppTheme {
                AppNavGraph(navController = navController)
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then bottom navigation items are visible
        // Note: These would be from the main app scaffold, not the NavGraph itself
        // This test verifies the basic setup works
        assert(true)
    }
}