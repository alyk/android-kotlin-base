package com.example.feature.detail

import app.cash.turbine.test
import com.example.core.data.repository.GameRepository
import com.example.core.data.repository.UserRepository
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for DetailViewModel.
 * Tests game detail loading, favourite toggling, and state management.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var mockGameRepository: GameRepository
    private lateinit var mockUserRepository: UserRepository

    private val testDispatcher = StandardTestDispatcher()

    private val testGame = Game(
        id = 1L,
        title = "Test Game",
        description = "A test game description",
        thumbnailUrl = "https://example.com/thumb.png",
        genre = com.example.core.model.Genre.ACTION,
        platform = com.example.core.model.Platform.PC,
        developer = "Test Developer",
        publisher = "Test Publisher",
        releaseDate = "2024-01-15",
        rating = 4.5f,
        price = 59.99
    )

    private val testGameDetail = GameDetail(
        game = testGame,
        screenshots = listOf("screenshot1.png", "screenshot2.png"),
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
    fun `initial state is correct`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(0L, state.gameId)
            assertNull(state.game)
            assertFalse(state.isLoading)
            assertFalse(state.isFavourite)
            assertNull(state.error)
            assertTrue(state.screenshots.isEmpty())
        }
    }

    @Test
    fun `LoadGame updates state with game details`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1L, state.gameId)
            assertEquals("Test Game", state.game?.title)
            assertEquals(2, state.screenshots.size)
            assertFalse(state.isLoading)
            assertFalse(state.isFavourite)
        }
    }

    @Test
    fun `LoadGame shows favourite status`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns true

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isFavourite)
        }
    }

    @Test
    fun `LoadGame sets error on failure`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Error("Game not found")

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Game not found", state.error)
            assertFalse(state.isLoading)
            assertNull(state.game)
        }
    }

    @Test
    fun `RefreshGame reloads current game`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)

        viewModel.handleIntent(GameDetailIntent.RefreshGame)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Test Game", state.game?.title)
        }
    }

    @Test
    fun `ToggleFavourite adds favourite and shows message`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(false, true)
        coEvery { mockUserRepository.addFavourite(1L) } returns Result.Success(Unit)

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(GameDetailIntent.ToggleFavourite)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is GameDetailEffect.ShowMessage)
            assertEquals("Added to favourites", (effect as GameDetailEffect.ShowMessage).message)
        }

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isFavourite)
        }
    }

    @Test
    fun `ToggleFavourite removes favourite and shows message`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(true, false)
        coEvery { mockUserRepository.removeFavourite(1L) } returns Result.Success(Unit)

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // First verify the effect is emitted
        viewModel.effects.test {
            viewModel.handleIntent(GameDetailIntent.ToggleFavourite)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is GameDetailEffect.ShowMessage)
            assertEquals("Removed from favourites", (effect as GameDetailEffect.ShowMessage).message)
        }

        // Then verify the state was updated
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isFavourite)
        }
    }

    @Test
    fun `GameClicked emits ShowScreenshot effect`() = runTest {
        viewModel.effects.test {
            viewModel.handleIntent(GameDetailIntent.GameClicked("https://example.com/screen.png"))

            val effect = awaitItem()
            assertTrue(effect is GameDetailEffect.ShowScreenshot)
            assertEquals("https://example.com/screen.png", (effect as GameDetailEffect.ShowScreenshot).url)
        }
    }

    @Test
    fun `VisitWebsite emits OpenUrl effect with thumbnail URL`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returns false

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(GameDetailIntent.VisitWebsite)

            val effect = awaitItem()
            assertTrue(effect is GameDetailEffect.OpenUrl)
            assertEquals("https://example.com/thumb.png", (effect as GameDetailEffect.OpenUrl).url)
        }
    }

    @Test
    fun `ClearError removes error from state`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Error("Network error")

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(GameDetailIntent.ClearError)

        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }

    @Test
    fun `ToggleFavourite does nothing when game is null`() = runTest {
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite)

        coVerify(exactly = 0) { mockUserRepository.addFavourite(any()) }
        coVerify(exactly = 0) { mockUserRepository.removeFavourite(any()) }
    }

    @Test
    fun `ToggleFavourite still shows success message even when repository operation fails`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(false, false)
        coEvery { mockUserRepository.addFavourite(1L) } returns Result.Error("Database error")

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(GameDetailIntent.ToggleFavourite)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is GameDetailEffect.ShowMessage)
            assertEquals("Added to favourites", (effect as GameDetailEffect.ShowMessage).message)
        }

        // The UI state will be updated optimistically, but the actual favourite status check will show the correct state
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isFavourite) // Repository failure means it's not actually favourited
        }
    }

    @Test
    fun `ToggleFavourite updates UI state immediately for optimistic UI`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(false, true)
        coEvery { mockUserRepository.addFavourite(1L) } returns Result.Success(Unit)

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify initial state is not favourite
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertFalse(initialState.isFavourite)
        }

        // Toggle favourite and verify immediate UI update
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite)
        testDispatcher.scheduler.advanceUntilIdle()

        // UI should update optimistically before database operation completes
        viewModel.uiState.test {
            val updatedState = awaitItem()
            assertTrue(updatedState.isFavourite)
        }
    }

    @Test
    fun `ToggleFavourite handles multiple rapid toggles correctly`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(false, true, false, true)
        coEvery { mockUserRepository.addFavourite(1L) } returns Result.Success(Unit)
        coEvery { mockUserRepository.removeFavourite(1L) } returns Result.Success(Unit)

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // Perform multiple rapid toggles with proper synchronization
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite) // Add
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite) // Remove
        testDispatcher.scheduler.advanceUntilIdle()
        
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite) // Add again
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify final state is favourite (last operation was add)
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isFavourite)
        }

        // Verify repository calls were made
        coVerify(exactly = 2) { mockUserRepository.addFavourite(1L) }
        coVerify(exactly = 1) { mockUserRepository.removeFavourite(1L) }
    }

    @Test
    fun `ToggleFavourite maintains correct state after refresh`() = runTest {
        coEvery { mockGameRepository.getGameById(1L) } returns Result.Success(testGameDetail)
        coEvery { mockUserRepository.isFavourited(1L) } returnsMany listOf(false, true, true)
        coEvery { mockUserRepository.addFavourite(1L) } returns Result.Success(Unit)

        viewModel.handleIntent(GameDetailIntent.LoadGame(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // Add to favourites
        viewModel.handleIntent(GameDetailIntent.ToggleFavourite)
        testDispatcher.scheduler.advanceUntilIdle()

        // Refresh the game
        viewModel.handleIntent(GameDetailIntent.RefreshGame)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify favourite status is preserved after refresh
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isFavourite)
        }
    }
}