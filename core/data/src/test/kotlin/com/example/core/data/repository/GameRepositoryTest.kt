package com.example.core.data.repository

import com.example.core.data.datasource.GameRemoteDataSource
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Result
import com.example.core.model.SearchResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for GameRepositoryImpl.
 * Tests all repository methods with mocked data sources.
 */
class GameRepositoryTest {

    private lateinit var repository: GameRepositoryImpl
    private lateinit var mockDataSource: GameRemoteDataSource

    private val testGames = listOf(
        Game(id = 1, title = "Game 1"),
        Game(id = 2, title = "Game 2"),
        Game(id = 3, title = "Game 3")
    )

    private val testGameDetail = GameDetail(
        game = Game(id = 1, title = "Game 1"),
        screenshots = listOf("screenshot1.png"),
        systemRequirements = null
    )

    private val testSearchResult = SearchResult(
        games = testGames,
        totalCount = 3,
        page = 1,
        pageSize = 20,
        hasMore = false
    )

    @Before
    fun setup() {
        mockDataSource = mockk()
        repository = GameRepositoryImpl(mockDataSource)
    }

    @Test
    fun `getGames returns success when data source succeeds`() = runTest {
        coEvery { mockDataSource.getGames(1, 20) } returns Result.Success(testGames)

        val result = repository.getGames(1, 20)

        assertTrue(result is Result.Success)
        assertEquals(3, (result as Result.Success).data.size)
    }

    @Test
    fun `getGames returns error when data source fails`() = runTest {
        coEvery { mockDataSource.getGames(1, 20) } returns Result.Error("Network error")

        val result = repository.getGames(1, 20)

        assertTrue(result is Result.Error)
        assertEquals("Network error", (result as Result.Error).message)
    }

    @Test
    fun `getFeaturedGames returns success with featured games`() = runTest {
        val featuredGames = listOf(Game(id = 10, title = "Featured Game", isFeatured = true))
        coEvery { mockDataSource.getFeaturedGames() } returns Result.Success(featuredGames)

        val result = repository.getFeaturedGames()

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertTrue(result.data[0].isFeatured)
    }

    @Test
    fun `getFeaturedGames returns error on failure`() = runTest {
        coEvery { mockDataSource.getFeaturedGames() } returns Result.Error("Failed to fetch")

        val result = repository.getFeaturedGames()

        assertTrue(result is Result.Error)
        assertEquals("Failed to fetch", (result as Result.Error).message)
    }

    @Test
    fun `getGameById returns success with game detail`() = runTest {
        coEvery { mockDataSource.getGameById(1L) } returns Result.Success(testGameDetail)

        val result = repository.getGameById(1L)

        assertTrue(result is Result.Success)
        assertEquals("Game 1", (result as Result.Success).data.game.title)
    }

    @Test
    fun `getGameById returns error when game not found`() = runTest {
        coEvery { mockDataSource.getGameById(999L) } returns Result.Error("Game not found")

        val result = repository.getGameById(999L)

        assertTrue(result is Result.Error)
        assertEquals("Game not found", (result as Result.Error).message)
    }

    @Test
    fun `searchGames returns success with search results`() = runTest {
        coEvery { mockDataSource.searchGames("test", 1, 20) } returns Result.Success(testSearchResult)

        val result = repository.searchGames("test", 1, 20)

        assertTrue(result is Result.Success)
        assertEquals(3, (result as Result.Success).data.games.size)
        assertEquals(3, result.data.totalCount)
        assertFalse(result.data.hasMore)
    }

    @Test
    fun `searchGames returns error on failure`() = runTest {
        coEvery { mockDataSource.searchGames("test", 1, 20) } returns Result.Error("Search failed")

        val result = repository.searchGames("test", 1, 20)

        assertTrue(result is Result.Error)
        assertEquals("Search failed", (result as Result.Error).message)
    }

    @Test
    fun `getGamesByGenre returns filtered games`() = runTest {
        val actionGames = listOf(
            Game(id = 1, title = "Action Game", genre = com.example.core.model.Genre.ACTION)
        )
        coEvery { mockDataSource.getGamesByGenre("ACTION", 1, 20) } returns Result.Success(actionGames)

        val result = repository.getGamesByGenre("ACTION", 1, 20)

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(com.example.core.model.Genre.ACTION, result.data[0].genre)
    }

    @Test
    fun `getGamesByGenre returns error on failure`() = runTest {
        coEvery { mockDataSource.getGamesByGenre("ACTION", 1, 20) } returns Result.Error("Filter failed")

        val result = repository.getGamesByGenre("ACTION", 1, 20)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `getGamesByPlatform returns filtered games`() = runTest {
        val pcGames = listOf(
            Game(id = 1, title = "PC Game", platform = com.example.core.model.Platform.PC)
        )
        coEvery { mockDataSource.getGamesByPlatform("PC", 1, 20) } returns Result.Success(pcGames)

        val result = repository.getGamesByPlatform("PC", 1, 20)

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(com.example.core.model.Platform.PC, result.data[0].platform)
    }

    @Test
    fun `getGamesByPlatform returns error on failure`() = runTest {
        coEvery { mockDataSource.getGamesByPlatform("PC", 1, 20) } returns Result.Error("Filter failed")

        val result = repository.getGamesByPlatform("PC", 1, 20)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `getGames uses default pagination values`() = runTest {
        coEvery { mockDataSource.getGames(1, 20) } returns Result.Success(testGames)

        val result = repository.getGames()

        assertTrue(result is Result.Success)
    }

    @Test
    fun `searchGames uses default pagination values`() = runTest {
        coEvery { mockDataSource.searchGames("test", 1, 20) } returns Result.Success(testSearchResult)

        val result = repository.searchGames("test")

        assertTrue(result is Result.Success)
    }
}