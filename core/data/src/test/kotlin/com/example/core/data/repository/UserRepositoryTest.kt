package com.example.core.data.repository

import com.example.core.database.repository.UserLocalRepository
import com.example.core.model.FavouriteWithGame
import com.example.core.model.Game
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for UserRepositoryImpl.
 * Tests user-related operations with mocked local repository.
 */
class UserRepositoryTest {

    private lateinit var repository: UserRepositoryImpl
    private lateinit var mockLocalRepository: UserLocalRepository

    private val testGames = listOf(
        Game(id = 1, title = "Favourite Game 1"),
        Game(id = 2, title = "Favourite Game 2")
    )

    @Before
    fun setup() {
        mockLocalRepository = mockk()
        repository = UserRepositoryImpl(mockLocalRepository)
    }

    @Test
    fun `getFavourites returns games from local repository`() = runTest {
        val favouriteWithGames = listOf(
            FavouriteWithGame(
                favourite = com.example.core.model.Favourite(id = 1, userId = 1, gameId = 1, addedAt = "2024-01-01"),
                game = testGames[0]
            ),
            FavouriteWithGame(
                favourite = com.example.core.model.Favourite(id = 2, userId = 1, gameId = 2, addedAt = "2024-01-02"),
                game = testGames[1]
            )
        )
        every { mockLocalRepository.observeFavouritesWithGames(1L) } returns flowOf(favouriteWithGames)

        val result = repository.getFavourites().first()

        assertEquals(2, result.size)
        assertEquals("Favourite Game 1", result[0].title)
        assertEquals("Favourite Game 2", result[1].title)
    }

    @Test
    fun `getFavourites returns empty list when no favourites`() = runTest {
        every { mockLocalRepository.observeFavouritesWithGames(1L) } returns flowOf(emptyList())

        val result = repository.getFavourites().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `addFavourite calls local repository`() = runTest {
        coEvery { mockLocalRepository.addFavourite(1L, 5L) } returns Result.Success(1L)

        val result = repository.addFavourite(5L)

        assertTrue(result is Result.Success)
        coVerify { mockLocalRepository.addFavourite(1L, 5L) }
    }

    @Test
    fun `addFavourite returns error when local repository fails`() = runTest {
        coEvery { mockLocalRepository.addFavourite(1L, 5L) } returns Result.Error("Database error")

        val result = repository.addFavourite(5L)

        assertTrue(result is Result.Error)
        assertEquals("Database error", (result as Result.Error).message)
    }

    @Test
    fun `removeFavourite calls local repository`() = runTest {
        coEvery { mockLocalRepository.removeFavourite(1L, 5L) } returns Result.Success(Unit)

        val result = repository.removeFavourite(5L)

        assertTrue(result is Result.Success)
        coVerify { mockLocalRepository.removeFavourite(1L, 5L) }
    }

    @Test
    fun `removeFavourite returns error when local repository fails`() = runTest {
        coEvery { mockLocalRepository.removeFavourite(1L, 5L) } returns Result.Error("Not found")

        val result = repository.removeFavourite(5L)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `isFavourited returns true when game is favourited`() = runTest {
        coEvery { mockLocalRepository.isFavourited(1L, 5L) } returns Result.Success(true)

        val result = repository.isFavourited(5L)

        assertTrue(result)
    }

    @Test
    fun `isFavourited returns false when game is not favourited`() = runTest {
        coEvery { mockLocalRepository.isFavourited(1L, 5L) } returns Result.Success(false)

        val result = repository.isFavourited(5L)

        assertFalse(result)
    }

    @Test
    fun `isFavourited returns false when local repository returns error`() = runTest {
        coEvery { mockLocalRepository.isFavourited(1L, 5L) } returns Result.Error("Database error")

        val result = repository.isFavourited(5L)

        assertFalse(result)
    }

    @Test
    fun `isFavourited returns false when local repository returns loading`() = runTest {
        coEvery { mockLocalRepository.isFavourited(1L, 5L) } returns Result.Loading

        val result = repository.isFavourited(5L)

        assertFalse(result)
    }
}