package com.example.feature.favourites

import app.cash.turbine.test
import com.example.core.database.repository.FavouritesRepository
import com.example.core.model.LocalFavourite
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModelTest {

    private lateinit var mockRepository: FavouritesRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): FavouritesViewModel {
        return FavouritesViewModel(mockRepository)
    }

    @Test
    fun initialStateShouldBeLoadingWithEmptyList() = runTest {
        // Given - mock returns empty list for observeAllFavourites
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())

        // When
        val viewModel = createViewModel()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.favouriteGames.isEmpty())
        assertTrue(state.isEmpty)
        assertNull(state.error)
    }

    @Test
    fun loadFavouritesShouldUpdateStateWithSuccessResult() = runTest {
        // Given
        val testFavourites = listOf(
            LocalFavourite(
                id = 1,
                gameId = 100,
                title = "Test Game 1",
                thumbnailUrl = "url1",
                genre = "Action",
                platform = "PC",
                rating = 4.5f,
                savedAt = 1234567890
            )
        )
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.getAllFavourites() } returns Result.Success(testFavourites)
        val viewModel = createViewModel()

        // When
        viewModel.handleIntent(FavouritesIntent.LoadFavourites)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(testFavourites, state.favouriteGames)
        assertFalse(state.isEmpty)
        assertNull(state.error)
        coVerify { mockRepository.getAllFavourites() }
    }

    @Test
    fun loadFavouritesShouldUpdateStateWithErrorResult() = runTest {
        // Given
        val errorMessage = "Failed to load favourites"
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.getAllFavourites() } returns Result.Error(errorMessage)
        val viewModel = createViewModel()

        // When
        viewModel.handleIntent(FavouritesIntent.LoadFavourites)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.favouriteGames.isEmpty())
        assertTrue(state.isEmpty)
        assertEquals(errorMessage, state.error)
        coVerify { mockRepository.getAllFavourites() }
    }

    @Test
    fun refreshFavouritesShouldReloadFavourites() = runTest {
        // Given
        val testFavourites = listOf(
            LocalFavourite(
                id = 1,
                gameId = 100,
                title = "Test Game 1",
                thumbnailUrl = "url1",
                genre = "Action",
                platform = "PC",
                rating = 4.5f,
                savedAt = 1234567890
            )
        )
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.getAllFavourites() } returns Result.Success(testFavourites)
        val viewModel = createViewModel()

        // When
        viewModel.handleIntent(FavouritesIntent.RefreshFavourites)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(testFavourites, state.favouriteGames)
        assertFalse(state.isEmpty)
        assertNull(state.error)
        coVerify { mockRepository.getAllFavourites() }
    }

    @Test
    fun removeFavouriteShouldRemoveGameAndEmitSuccessEffect() = runTest {
        // Given
        val gameIdToRemove = 100L
        val testFavourites = listOf(
            LocalFavourite(
                id = 1,
                gameId = gameIdToRemove,
                title = "Test Game 1",
                thumbnailUrl = "url1",
                genre = "Action",
                platform = "PC",
                rating = 4.5f,
                savedAt = 1234567890
            ),
            LocalFavourite(
                id = 2,
                gameId = 200,
                title = "Test Game 2",
                thumbnailUrl = "url2",
                genre = "RPG",
                platform = "PS5",
                rating = 4.8f,
                savedAt = 1234567891
            )
        )
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(testFavourites)
        coEvery { mockRepository.removeFavourite(gameIdToRemove) } returns Result.Success(Unit)
        val viewModel = createViewModel()

        // When - start collecting effects first
        viewModel.effects.test {
            // When
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(gameIdToRemove))

            // Then
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowFavouriteRemoved)
            assertEquals("Test Game 1", (effect as FavouritesEffect.ShowFavouriteRemoved).gameTitle)
        }
        coVerify { mockRepository.removeFavourite(gameIdToRemove) }
    }

    @Test
    fun removeFavouriteShouldEmitErrorEffectWhenRemovalFails() = runTest {
        // Given
        val gameIdToRemove = 100L
        val errorMessage = "Failed to remove favourite"
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.removeFavourite(gameIdToRemove) } returns Result.Error(errorMessage)
        val viewModel = createViewModel()

        // When - start collecting effects first
        viewModel.effects.test {
            // When
            viewModel.handleIntent(FavouritesIntent.RemoveFavourite(gameIdToRemove))

            // Then
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowError)
            assertEquals(errorMessage, (effect as FavouritesEffect.ShowError).message)
        }
        coVerify { mockRepository.removeFavourite(gameIdToRemove) }
    }

    @Test
    fun gameClickedShouldEmitNavigateEffect() = runTest {
        // Given
        val gameIdToNavigate = 100L
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        val viewModel = createViewModel()

        // When - start collecting effects first
        viewModel.effects.test {
            // When
            viewModel.handleIntent(FavouritesIntent.GameClicked(gameIdToNavigate))

            // Then
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.NavigateToGameDetail)
            assertEquals(gameIdToNavigate, (effect as FavouritesEffect.NavigateToGameDetail).gameId)
        }
    }

    @Test
    fun clearErrorShouldRemoveErrorFromState() = runTest {
        // Given
        val errorMessage = "Test error"
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        // Mock to return success on second call for clearError
        coEvery { mockRepository.getAllFavourites() } returns Result.Error(errorMessage)
        val viewModel = createViewModel()

        // First set an error
        viewModel.handleIntent(FavouritesIntent.LoadFavourites)
        assertEquals(errorMessage, viewModel.uiState.value.error)

        // When - mock returns success now
        coEvery { mockRepository.getAllFavourites() } returns Result.Success(emptyList())
        viewModel.handleIntent(FavouritesIntent.ClearError)

        // Then
        val state = viewModel.uiState.value
        assertNull(state.error)
    }

    @Test
    fun observeFavouritesShouldUpdateStateWhenRepositoryEmitsNewData() = runTest {
        // Given
        val testFavourites = listOf(
            LocalFavourite(
                id = 1,
                gameId = 100,
                title = "Test Game 1",
                thumbnailUrl = "url1",
                genre = "Action",
                platform = "PC",
                rating = 4.5f,
                savedAt = 1234567890
            )
        )
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(testFavourites)

        // When
        val viewModel = createViewModel()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(testFavourites, state.favouriteGames)
        assertFalse(state.isEmpty)
        assertNull(state.error)
    }

    @Test
    fun emptyFavouritesListShouldSetIsEmptyToTrue() = runTest {
        // Given
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())

        // When
        val viewModel = createViewModel()

        // Then
        assertTrue(viewModel.uiState.value.isEmpty)
    }

    @Test
    fun addFavouriteShouldAddGameAndEmitSuccessEffect() = runTest {
        // Given
        val newFavourite = LocalFavourite(
            id = 1,
            gameId = 100,
            title = "New Game",
            thumbnailUrl = "url1",
            genre = "Action",
            platform = "PC",
            rating = 4.5f,
            savedAt = 1234567890
        )
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.addFavourite(newFavourite) } returns Result.Success(Unit)
        val viewModel = createViewModel()

        // When - start collecting effects first
        viewModel.effects.test {
            // When
            viewModel.handleIntent(FavouritesIntent.AddFavourite(newFavourite))

            // Then
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowFavouriteAdded)
            assertEquals("New Game", (effect as FavouritesEffect.ShowFavouriteAdded).gameTitle)
        }
        coVerify { mockRepository.addFavourite(newFavourite) }
    }

    @Test
    fun addFavouriteShouldEmitErrorEffectWhenAdditionFails() = runTest {
        // Given
        val newFavourite = LocalFavourite(
            id = 1,
            gameId = 100,
            title = "New Game",
            thumbnailUrl = "url1",
            genre = "Action",
            platform = "PC",
            rating = 4.5f,
            savedAt = 1234567890
        )
        val errorMessage = "Failed to add favourite"
        coEvery { mockRepository.observeAllFavourites() } returns flowOf(emptyList())
        coEvery { mockRepository.addFavourite(newFavourite) } returns Result.Error(errorMessage)
        val viewModel = createViewModel()

        // When - start collecting effects first
        viewModel.effects.test {
            // When
            viewModel.handleIntent(FavouritesIntent.AddFavourite(newFavourite))

            // Then
            val effect = awaitItem()
            assertTrue(effect is FavouritesEffect.ShowError)
            assertEquals(errorMessage, (effect as FavouritesEffect.ShowError).message)
        }
        coVerify { mockRepository.addFavourite(newFavourite) }
    }
}