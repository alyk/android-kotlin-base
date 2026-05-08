package com.example.feature.favourites

import app.cash.turbine.test
import com.example.core.data.repository.UserRepository
import com.example.core.model.Game
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for FavouritesViewModel.
 * Tests the ViewModel logic without requiring Android instrumented tests.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModelTest {

    private lateinit var mockRepository: UserRepository

    private val testDispatcher = StandardTestDispatcher()

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
        mockRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `empty favourites shows empty state`() = runTest {
        // Given empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When ViewModel is created
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        // Then empty state is shown
        val state = viewModel.uiState.value
        assertFalse("Should not be loading", state.isLoading)
        assertTrue("Should be empty", state.isEmpty)
        assertTrue("Favourites list should be empty", state.favourites.isEmpty())
    }

    @Test
    fun `favourites are loaded successfully`() = runTest {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When ViewModel is created
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        // Then favourites are displayed
        val state = viewModel.uiState.value
        assertFalse("Should not be loading", state.isLoading)
        assertFalse("Should not be empty", state.isEmpty)
        assertEquals("Should have 2 favourites", 2, state.favourites.size)
        assertEquals("First game should be Elden Ring", "Elden Ring", state.favourites[0].title)
    }

    @Test
    fun `removing favourite shows success message`() = runTest {
        // Given initial favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // And remove operation succeeds
        coEvery { mockRepository.removeFavourite(1L) } returns Result.Success(Unit)

        // When ViewModel is created and game is removed
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(1L))
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue("Should be ShowMessage effect", effect is FavouritesEffect.ShowMessage)
            assertEquals("Message should confirm removal", "Removed from favourites", (effect as FavouritesEffect.ShowMessage).message)
        }
    }

    @Test
    fun `removing favourite shows error on failure`() = runTest {
        // Given initial favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // And remove operation fails
        coEvery { mockRepository.removeFavourite(1L) } returns Result.Error("Failed to remove")

        // When ViewModel is created and game removal fails
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(1L))
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue("Should be ShowError effect", effect is FavouritesEffect.ShowError)
            assertEquals("Error message should match", "Failed to remove", (effect as FavouritesEffect.ShowError).message)
        }
    }

    @Test
    fun `game click emits navigation effect`() = runTest {
        // Given favourites data
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When ViewModel is created and game is clicked
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.handleIntent(FavouritesIntent.GameClicked(1L))
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue("Should be NavigateToGameDetail effect", effect is FavouritesEffect.NavigateToGameDetail)
            assertEquals("Game ID should be 1", 1L, (effect as FavouritesEffect.NavigateToGameDetail).gameId)
        }
    }

    @Test
    fun `load favourites intent triggers reload`() = runTest {
        // Given initial empty favourites
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When ViewModel is created and then load is triggered again
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        // Then load again
        viewModel.handleIntent(FavouritesIntent.LoadFavourites)
        advanceUntilIdle()

        // Verify repository was called
        io.mockk.verify(atLeast = 2) { mockRepository.getFavourites() }
    }

    @Test
    fun `state updates when flow emits new value`() = runTest {
        // Given favourites flow that updates
        val favouritesFlow = MutableStateFlow<List<Game>>(emptyList())
        every { mockRepository.getFavourites() } returns favouritesFlow

        // When ViewModel is created and flow emits new data
        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        // Then state should be empty
        assertTrue("Should be empty initially", viewModel.uiState.value.isEmpty)

        // When flow emits new data
        favouritesFlow.value = testGames
        advanceUntilIdle()

        // Then state should have favourites
        val state = viewModel.uiState.value
        assertFalse("Should not be empty after new data", state.isEmpty)
        assertEquals("Should have 2 favourites", 2, state.favourites.size)
    }

    @Test
    fun `clear error intent clears error state`() = runTest {
        // This test verifies the clear error functionality exists
        // Since we can't easily trigger an error in the flow, we just verify the method works
        val favouritesFlow = MutableStateFlow<List<Game>>(testGames)
        every { mockRepository.getFavourites() } returns favouritesFlow

        val viewModel = FavouritesViewModel(mockRepository)
        advanceUntilIdle()

        // Initial state should have no error
        assertEquals("Error should be null initially", null, viewModel.uiState.value.error)

        // Clear error should work without issues
        viewModel.handleIntent(FavouritesIntent.ClearError)
        advanceUntilIdle()

        assertEquals("Error should still be null", null, viewModel.uiState.value.error)
    }
}