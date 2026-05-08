package com.example.core.database.repository

import com.example.core.database.dao.FavouriteGameDao
import com.example.core.database.entity.FavouriteGameEntity
import com.example.core.model.LocalFavourite
import com.example.core.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FavouritesRepositoryTest {

    private lateinit var mockDao: FavouriteGameDao
    private lateinit var repository: FavouritesRepositoryImpl

    @Before
    fun setUp() {
        mockDao = mockk(relaxed = true)
        repository = FavouritesRepositoryImpl(mockDao)
    }

    private fun createTestEntity(
        id: Long = 1,
        gameId: Long = 100,
        title: String = "Test Game",
        thumbnailUrl: String = "https://example.com/thumb.jpg",
        genre: String = "Action",
        platform: String = "PC",
        rating: Float = 4.5f,
        savedAt: Long = 1234567890
    ): FavouriteGameEntity {
        return FavouriteGameEntity(
            id = id,
            gameId = gameId,
            title = title,
            thumbnailUrl = thumbnailUrl,
            genre = genre,
            platform = platform,
            rating = rating,
            savedAt = savedAt
        )
    }

    private fun createTestFavourite(
        id: Long = 1,
        gameId: Long = 100,
        title: String = "Test Game",
        thumbnailUrl: String = "https://example.com/thumb.jpg",
        genre: String = "Action",
        platform: String = "PC",
        rating: Float = 4.5f,
        savedAt: Long = 1234567890
    ): LocalFavourite {
        return LocalFavourite(
            id = id,
            gameId = gameId,
            title = title,
            thumbnailUrl = thumbnailUrl,
            genre = genre,
            platform = platform,
            rating = rating,
            savedAt = savedAt
        )
    }

    // ==================== addFavourite Tests ====================

    @Test
    fun `addFavourite should return success when dao insert succeeds`() = runTest {
        // Given
        val favourite = createTestFavourite()
        val entitySlot = slot<FavouriteGameEntity>()
        coEvery { mockDao.insert(capture(entitySlot)) } returns Unit

        // When
        val result = repository.addFavourite(favourite)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify { mockDao.insert(any()) }
        assertEquals(favourite.gameId, entitySlot.captured.gameId)
        assertEquals(favourite.title, entitySlot.captured.title)
    }

    @Test
    fun `addFavourite should return error when dao throws exception`() = runTest {
        // Given
        val favourite = createTestFavourite()
        val exceptionMessage = "Database error"
        coEvery { mockDao.insert(any()) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.addFavourite(favourite)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to add favourite"))
        assertTrue(result.message.contains(exceptionMessage))
    }

    // ==================== removeFavourite by gameId Tests ====================

    @Test
    fun `removeFavourite by gameId should return success when dao delete succeeds`() = runTest {
        // Given
        val gameId = 100L
        coEvery { mockDao.deleteByGameId(gameId) } returns Unit

        // When
        val result = repository.removeFavourite(gameId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify { mockDao.deleteByGameId(gameId) }
    }

    @Test
    fun `removeFavourite by gameId should return error when dao throws exception`() = runTest {
        // Given
        val gameId = 100L
        val exceptionMessage = "Delete failed"
        coEvery { mockDao.deleteByGameId(gameId) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.removeFavourite(gameId)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to remove favourite"))
    }

    // ==================== removeFavourite by entity Tests ====================

    @Test
    fun `removeFavourite by entity should return success when dao delete succeeds`() = runTest {
        // Given
        val favourite = createTestFavourite()
        val entitySlot = slot<FavouriteGameEntity>()
        coEvery { mockDao.delete(capture(entitySlot)) } returns Unit

        // When
        val result = repository.removeFavourite(favourite)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify { mockDao.delete(any()) }
        assertEquals(favourite.id, entitySlot.captured.id)
        assertEquals(favourite.gameId, entitySlot.captured.gameId)
    }

    @Test
    fun `removeFavourite by entity should return error when dao throws exception`() = runTest {
        // Given
        val favourite = createTestFavourite()
        val exceptionMessage = "Delete failed"
        coEvery { mockDao.delete(any()) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.removeFavourite(favourite)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to remove favourite"))
    }

    // ==================== getFavouriteByGameId Tests ====================

    @Test
    fun `getFavouriteByGameId should return favourite when found`() = runTest {
        // Given
        val gameId = 100L
        val entity = createTestEntity(gameId = gameId)
        coEvery { mockDao.getFavouriteByGameId(gameId) } returns entity

        // When
        val result = repository.getFavouriteByGameId(gameId)

        // Then
        assertTrue(result is Result.Success)
        val favourite = (result as Result.Success).data
        assertEquals(gameId, favourite?.gameId)
        assertEquals(entity.title, favourite?.title)
    }

    @Test
    fun `getFavouriteByGameId should return null when not found`() = runTest {
        // Given
        val gameId = 100L
        coEvery { mockDao.getFavouriteByGameId(gameId) } returns null

        // When
        val result = repository.getFavouriteByGameId(gameId)

        // Then
        assertTrue(result is Result.Success)
        assertNull((result as Result.Success).data)
    }

    @Test
    fun `getFavouriteByGameId should return error when dao throws exception`() = runTest {
        // Given
        val gameId = 100L
        val exceptionMessage = "Query failed"
        coEvery { mockDao.getFavouriteByGameId(gameId) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.getFavouriteByGameId(gameId)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to get favourite"))
    }

    // ==================== getAllFavourites Tests ====================

    @Test
    fun `getAllFavourites should return list of favourites`() = runTest {
        // Given
        val entities = listOf(
            createTestEntity(id = 1, gameId = 100, title = "Game 1"),
            createTestEntity(id = 2, gameId = 200, title = "Game 2")
        )
        coEvery { mockDao.getAllFavouritesList() } returns entities

        // When
        val result = repository.getAllFavourites()

        // Then
        assertTrue(result is Result.Success)
        val favourites = (result as Result.Success).data
        assertEquals(2, favourites.size)
        assertEquals("Game 1", favourites[0].title)
        assertEquals("Game 2", favourites[1].title)
    }

    @Test
    fun `getAllFavourites should return empty list when no favourites exist`() = runTest {
        // Given
        coEvery { mockDao.getAllFavouritesList() } returns emptyList()

        // When
        val result = repository.getAllFavourites()

        // Then
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }

    @Test
    fun `getAllFavourites should return error when dao throws exception`() = runTest {
        // Given
        val exceptionMessage = "Query failed"
        coEvery { mockDao.getAllFavouritesList() } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.getAllFavourites()

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to get favourites"))
    }

    // ==================== isFavourite Tests ====================

    @Test
    fun `isFavourite should return true when favourite exists`() = runTest {
        // Given
        val gameId = 100L
        val entity = createTestEntity(gameId = gameId)
        coEvery { mockDao.getFavouriteByGameId(gameId) } returns entity

        // When
        val result = repository.isFavourite(gameId)

        // Then
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data)
    }

    @Test
    fun `isFavourite should return false when favourite does not exist`() = runTest {
        // Given
        val gameId = 100L
        coEvery { mockDao.getFavouriteByGameId(gameId) } returns null

        // When
        val result = repository.isFavourite(gameId)

        // Then
        assertTrue(result is Result.Success)
        assertFalse((result as Result.Success).data)
    }

    @Test
    fun `isFavourite should return error when dao throws exception`() = runTest {
        // Given
        val gameId = 100L
        val exceptionMessage = "Query failed"
        coEvery { mockDao.getFavouriteByGameId(gameId) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.isFavourite(gameId)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to check favourite status"))
    }

    // ==================== getFavouriteCount Tests ====================

    @Test
    fun `getFavouriteCount should return correct count`() = runTest {
        // Given
        coEvery { mockDao.getFavouriteCount() } returns 5

        // When
        val result = repository.getFavouriteCount()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(5, (result as Result.Success).data)
    }

    @Test
    fun `getFavouriteCount should return zero when no favourites exist`() = runTest {
        // Given
        coEvery { mockDao.getFavouriteCount() } returns 0

        // When
        val result = repository.getFavouriteCount()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(0, (result as Result.Success).data)
    }

    @Test
    fun `getFavouriteCount should return error when dao throws exception`() = runTest {
        // Given
        val exceptionMessage = "Query failed"
        coEvery { mockDao.getFavouriteCount() } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.getFavouriteCount()

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to get favourite count"))
    }

    // ==================== clearAllFavourites Tests ====================

    @Test
    fun `clearAllFavourites should return success when dao delete succeeds`() = runTest {
        // Given
        coEvery { mockDao.deleteAllFavourites() } returns Unit

        // When
        val result = repository.clearAllFavourites()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify { mockDao.deleteAllFavourites() }
    }

    @Test
    fun `clearAllFavourites should return error when dao throws exception`() = runTest {
        // Given
        val exceptionMessage = "Delete failed"
        coEvery { mockDao.deleteAllFavourites() } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.clearAllFavourites()

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to clear favourites"))
    }

    // ==================== clearOldFavourites Tests ====================

    @Test
    fun `clearOldFavourites should return success when dao delete succeeds`() = runTest {
        // Given
        val timestamp = 1234567890L
        coEvery { mockDao.clearOldFavourites(timestamp) } returns Unit

        // When
        val result = repository.clearOldFavourites(timestamp)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify { mockDao.clearOldFavourites(timestamp) }
    }

    @Test
    fun `clearOldFavourites should return error when dao throws exception`() = runTest {
        // Given
        val timestamp = 1234567890L
        val exceptionMessage = "Delete failed"
        coEvery { mockDao.clearOldFavourites(timestamp) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.clearOldFavourites(timestamp)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to clear old favourites"))
    }

    // ==================== observeAllFavourites Tests ====================

    @Test
    fun `observeAllFavourites should return flow of favourites`() = runTest {
        // Given
        val entities = listOf(
            createTestEntity(id = 1, gameId = 100, title = "Game 1"),
            createTestEntity(id = 2, gameId = 200, title = "Game 2")
        )
        every { mockDao.getAllFavourites() } returns flowOf(entities)

        // When
        val flow = repository.observeAllFavourites()
        val result = flow.first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Game 1", result[0].title)
        assertEquals("Game 2", result[1].title)
    }

    @Test
    fun `observeAllFavourites should return empty flow when no favourites`() = runTest {
        // Given
        every { mockDao.getAllFavourites() } returns flowOf(emptyList())

        // When
        val flow = repository.observeAllFavourites()
        val result = flow.first()

        // Then
        assertTrue(result.isEmpty())
    }

    // ==================== observeFavouriteByGameId Tests ====================

    @Test
    fun `observeFavouriteByGameId should return favourite when found`() = runTest {
        // Given
        val gameId = 100L
        val entity = createTestEntity(gameId = gameId)
        every { mockDao.observeFavouriteByGameId(gameId) } returns flowOf(entity)

        // When
        val flow = repository.observeFavouriteByGameId(gameId)
        val result = flow.first()

        // Then
        assertEquals(gameId, result?.gameId)
        assertEquals(entity.title, result?.title)
    }

    @Test
    fun `observeFavouriteByGameId should return null when not found`() = runTest {
        // Given
        val gameId = 100L
        every { mockDao.observeFavouriteByGameId(gameId) } returns flowOf(null)

        // When
        val flow = repository.observeFavouriteByGameId(gameId)
        val result = flow.first()

        // Then
        assertNull(result)
    }

    // ==================== observeIsFavourite Tests ====================

    @Test
    fun `observeIsFavourite should return true when favourite exists`() = runTest {
        // Given
        val gameId = 100L
        every { mockDao.isFavourite(gameId) } returns flowOf(true)

        // When
        val flow = repository.observeIsFavourite(gameId)
        val result = flow.first()

        // Then
        assertTrue(result)
    }

    @Test
    fun `observeIsFavourite should return false when favourite does not exist`() = runTest {
        // Given
        val gameId = 100L
        every { mockDao.isFavourite(gameId) } returns flowOf(false)

        // When
        val flow = repository.observeIsFavourite(gameId)
        val result = flow.first()

        // Then
        assertFalse(result)
    }

    // ==================== observeFavouriteCount Tests ====================

    @Test
    fun `observeFavouriteCount should return correct count`() = runTest {
        // Given
        every { mockDao.observeFavouriteCount() } returns flowOf(10)

        // When
        val flow = repository.observeFavouriteCount()
        val result = flow.first()

        // Then
        assertEquals(10, result)
    }

    @Test
    fun `observeFavouriteCount should return zero when no favourites`() = runTest {
        // Given
        every { mockDao.observeFavouriteCount() } returns flowOf(0)

        // When
        val flow = repository.observeFavouriteCount()
        val result = flow.first()

        // Then
        assertEquals(0, result)
    }

    // ==================== Error Handling Tests ====================

    @Test
    fun `repository should handle database constraint violations gracefully`() = runTest {
        // Given
        val favourite = createTestFavourite()
        val exceptionMessage = "UNIQUE constraint failed"
        coEvery { mockDao.insert(any()) } throws android.database.sqlite.SQLiteConstraintException(exceptionMessage)

        // When
        val result = repository.addFavourite(favourite)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to add favourite"))
    }

    @Test
    fun `repository should handle various exception types consistently`() = runTest {
        // Given - Test with IllegalStateException
        val gameId = 100L
        coEvery { mockDao.getFavouriteByGameId(gameId) } throws IllegalStateException("Test error")

        // When
        val result = repository.getFavouriteByGameId(gameId)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).message.contains("Failed to get favourite"))
    }
}