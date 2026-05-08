package com.example.feature.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import com.example.core.ui.theme.GameAppTheme
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
 * UI tests for DetailScreen composable.
 * Tests game detail display, favourite functionality, and user interactions.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var mockGameRepository: com.example.core.data.repository.GameRepository
    private lateinit var mockUserRepository: com.example.core.data.repository.UserRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testGame = Game(
        id = 1L,
        title = "Test Game",
        description = "A test game description for UI testing",
        thumbnailUrl = "https://example.com/thumb.png",
        genre = Genre.ACTION,
        platform = Platform.PC,
        developer = "Test Developer",
        publisher = "Test Publisher",
        releaseDate = "2024-01-15",
        rating = 4.5f,
        price = 59.99
    )

    private val testGameDetail = GameDetail(
        game = testGame,
        screenshots = listOf(
            "https://example.com/screen1.png",
            "https://example.com/screen2.png"
        ),
        systemRequirements = null
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockGameRepository = mockk()
        mockUserRepository = mockk()
        viewModel = DetailViewModel(mockGameRepository, mockUserRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loading state is displayed initially`() {
        // Given loading state
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Loading
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for loading
        composeTestRule.waitForIdle()

        // Then loading screen is shown
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun `error state shows retry button`() {
        // Given error state
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Error("Game not found")
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for error
        composeTestRule.waitForIdle()

        // Then error screen with retry button is shown
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Game not found").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun `game details are displayed when loaded`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then game details are shown
        composeTestRule.onNodeWithText("Test Game").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
        composeTestRule.onNodeWithText("A test game description for UI testing").assertIsDisplayed()
    }

    @Test
    fun `game metadata is displayed correctly`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then game metadata is shown
        composeTestRule.onNodeWithText("Publisher").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Publisher").assertIsDisplayed()
        composeTestRule.onNodeWithText("Developer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Developer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Release Date").assertIsDisplayed()
        composeTestRule.onNodeWithText("2024-01-15").assertIsDisplayed()
    }

    @Test
    fun `back button triggers navigation`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        var backPressed = false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = { backPressed = true },
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Then navigation is triggered
        assert(backPressed)
    }

    @Test
    fun `favourite icon is displayed when not favourited`() {
        // Given game detail data with not favourited
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then favourite icon is shown as not filled
        composeTestRule.onNodeWithContentDescription("Add to favourites").assertIsDisplayed()
    }

    @Test
    fun `favourite icon is filled when favourited`() {
        // Given game detail data with favourited
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns true

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then favourite icon is shown as filled
        composeTestRule.onNodeWithContentDescription("Remove from favourites").assertIsDisplayed()
    }

    @Test
    fun `genre chip is displayed`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then genre chip is shown
        composeTestRule.onNodeWithText("ACTION").assertIsDisplayed()
    }

    @Test
    fun `platform badge is displayed`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then platform is shown
        composeTestRule.onNodeWithText("PC").assertIsDisplayed()
    }

    @Test
    fun `rating is displayed`() {
        // Given game detail data
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        // When DetailScreen is displayed
        composeTestRule.setContent {
            GameAppTheme {
                DetailScreen(
                    viewModel = viewModel,
                    gameId = 1L,
                    onNavigateBack = {},
                    onShowScreenshot = {},
                    onOpenUrl = {}
                )
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then rating is shown (with star symbol)
        composeTestRule.onNodeWithContentDescription("Rating").assertIsDisplayed()
    }
}