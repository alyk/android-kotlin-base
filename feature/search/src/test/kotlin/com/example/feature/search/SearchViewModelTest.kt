package com.example.feature.search

import app.cash.turbine.test
import com.example.core.data.repository.GameRepository
import com.example.core.model.Game
import com.example.core.model.Result
import com.example.core.model.SearchResult
import com.example.feature.search.SearchEffect
import com.example.feature.search.SearchIntent
import io.mockk.coEvery
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
 * Unit tests for SearchViewModel.
 * Tests search functionality, debouncing, and state management.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var mockRepository: GameRepository

    private val testDispatcher = StandardTestDispatcher()

    private val testGames = listOf(
        Game(id = 1, title = "Elden Ring"),
        Game(id = 2, title = "Cyberpunk 2077")
    )

    private val testSearchResult = SearchResult(
        games = testGames,
        totalCount = 2,
        page = 1,
        pageSize = 20,
        hasMore = false
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        viewModel = SearchViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertFalse(state.isLoading)
            assertTrue(state.searchResults.isEmpty())
            assertNull(state.error)
            assertFalse(state.hasSearched)
            assertFalse(state.isEmpty)
        }
    }

    @Test
    fun `UpdateQuery updates state correctly`() = runTest {
        coEvery { mockRepository.searchGames("elden") } returns Result.Success(testSearchResult)

        viewModel.handleIntent(SearchIntent.UpdateQuery("elden"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("elden", state.query)
        }
    }

    @Test
    fun `ClearQuery resets state correctly`() = runTest {
        viewModel.handleIntent(SearchIntent.UpdateQuery("test"))
        viewModel.handleIntent(SearchIntent.ClearQuery)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
            assertTrue(state.searchResults.isEmpty())
            assertFalse(state.hasSearched)
            assertFalse(state.isEmpty)
        }
    }

    @Test
    fun `search with empty query does nothing`() = runTest {
        viewModel.handleIntent(SearchIntent.Search)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.query)
        }
    }

    @Test
    fun `search returns success with results`() = runTest {
        coEvery { mockRepository.searchGames("elden") } returns Result.Success(testSearchResult)

        viewModel.handleIntent(SearchIntent.UpdateQuery("elden"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(2, state.searchResults.size)
            assertEquals("Elden Ring", state.searchResults[0].title)
            assertTrue(state.hasSearched)
            assertFalse(state.isEmpty)
        }
    }

    @Test
    fun `search returns empty results`() = runTest {
        val emptyResult = SearchResult(
            games = emptyList(),
            totalCount = 0,
            page = 1,
            pageSize = 20,
            hasMore = false
        )
        coEvery { mockRepository.searchGames("nonexistent") } returns Result.Success(emptyResult)

        viewModel.handleIntent(SearchIntent.UpdateQuery("nonexistent"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.searchResults.isEmpty())
            assertTrue(state.hasSearched)
            assertTrue(state.isEmpty)
        }
    }

    @Test
    fun `search returns error on failure`() = runTest {
        coEvery { mockRepository.searchGames("error") } returns Result.Error("Network error")

        viewModel.handleIntent(SearchIntent.UpdateQuery("error"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals("Network error", state.error)
            assertTrue(state.hasSearched)
        }
    }

    @Test
    fun `GameClicked emits NavigateToGameDetail effect`() = runTest {
        viewModel.effects.test {
            viewModel.handleIntent(SearchIntent.GameClicked(123L))

            val effect = awaitItem()
            assertTrue(effect is SearchEffect.NavigateToGameDetail)
            assertEquals(123L, (effect as SearchEffect.NavigateToGameDetail).gameId)
        }
    }

    @Test
    fun `ClearError removes error from state`() = runTest {
        coEvery { mockRepository.searchGames("error") } returns Result.Error("Network error")

        viewModel.handleIntent(SearchIntent.UpdateQuery("error"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(SearchIntent.ClearError)

        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }

    @Test
    fun `RefreshSearch triggers new search`() = runTest {
        coEvery { mockRepository.searchGames("test") } returns Result.Success(testSearchResult)

        viewModel.handleIntent(SearchIntent.UpdateQuery("test"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(SearchIntent.RefreshSearch)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(2, state.searchResults.size)
        }
    }

    @Test
    fun `blank query clears results`() = runTest {
        coEvery { mockRepository.searchGames("test") } returns Result.Success(testSearchResult)

        viewModel.handleIntent(SearchIntent.UpdateQuery("test"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify results exist
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.searchResults.size)
        }

        viewModel.handleIntent(SearchIntent.UpdateQuery("   "))
        testDispatcher.scheduler.advanceTimeBy(400) // Wait for debounce

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.searchResults.isEmpty())
            assertFalse(state.hasSearched)
        }
    }
}