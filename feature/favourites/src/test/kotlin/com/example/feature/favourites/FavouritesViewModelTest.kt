package com.example.feature.favourites

import app.cash.turbine.test
import com.example.core.data.repository.UserRepository
import com.example.core.model.Game
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
 * Unit tests for FavouritesViewModel.
 * Tests favourites loading, removal, and state management.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val testFavourites = listOf(
        Game(id = 1, title = "Favourite Game 1"),
        Game(id = 2, title = "Favourite Game 2"),
        Game(id = 3, title = "Favourite Game 3")
    )

    private lateinit var mockUserRepository: UserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUserRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): FavouritesViewModel {
        return FavouritesViewModel(mockUserRepository)
    }

    @Test
    fun `initial state loads favourites`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        val viewModel = createViewModel()

        // Advance the dispatcher to let the init block run
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(3, loadedState.favourites.size)
            assertFalse(loadedState.isEmpty)
        }
    }

    @Test
    fun `initial state shows empty when no favourites`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(emptyList())
        val viewModel = createViewModel()

        // Advance the dispatcher to let the init block run
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertTrue(loadedState.favourites.isEmpty())
            assertTrue(loadedState.isEmpty)
        }
    }

    @Test
    fun `LoadFavourites reloads favourites`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites.take(2))
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(FavouritesIntent.LoadFavourites)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.favourites.size)
        }
    }

    @Test
    fun `RefreshFavourites reloads current favourites`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(FavouritesIntent.RefreshFavourites)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(3, state.favourites.size)
        }
    }

    @Test
    fun `RemoveFavourite removes game and shows message`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        coEvery { mockUserRepository.removeFavourite(1L) } returns Result.Success(Unit)
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(1L))
            testDispatcher.scheduler.advanceUntilIdle()
            
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowMessage)
            assertEquals("Removed from favourites", (effect as FavouritesEffect.ShowMessage).message)
        }
    }

    @Test
    fun `RemoveFavourite shows error on failure`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        coEvery { mockUserRepository.removeFavourite(1L) } returns Result.Error("Database error")
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(1L))
            testDispatcher.scheduler.advanceUntilIdle()
            
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowError)
            assertEquals("Database error", (effect as FavouritesEffect.ShowError).message)
        }
    }

    @Test
    fun `GameClicked emits NavigateToGameDetail effect`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(emptyList())
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.GameClicked(123L))
            
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.NavigateToGameDetail)
            assertEquals(123L, (effect as FavouritesEffect.NavigateToGameDetail).gameId)
        }
    }

    @Test
    fun `ClearError removes error from state`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        coEvery { mockUserRepository.removeFavourite(1L) } returns Result.Error("Database error")
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        // First, trigger an error
        viewModel.handleIntent(FavouritesIntent.RemoveFavourite(1L))
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then clear it
        viewModel.handleIntent(FavouritesIntent.ClearError)

        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }

    @Test
    fun `favourites flow emits updates`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val loadedState = awaitItem()
            assertEquals(3, loadedState.favourites.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state correctly tracks isEmpty`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(emptyList())
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val emptyState = awaitItem()
            assertTrue(emptyState.isEmpty)
            assertTrue(emptyState.favourites.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state correctly tracks non-empty favourites`() = runTest {
        every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
        val viewModel = createViewModel()

        // Advance to complete initial load
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val nonEmptyState = awaitItem()
            assertFalse(nonEmptyState.isEmpty)
            assertEquals(3, nonEmptyState.favourites.size)

            cancelAndIgnoreRemainingEvents()
        }
    }
}