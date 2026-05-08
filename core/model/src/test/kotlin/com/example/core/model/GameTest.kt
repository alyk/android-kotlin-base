package com.example.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the Game data class.
 * Tests default values, properties, and equality.
 */
class GameTest {

    @Test
    fun `Game has correct default values`() {
        val game = Game()

        assertEquals(0L, game.id)
        assertEquals("", game.title)
        assertEquals("", game.description)
        assertEquals("", game.thumbnailUrl)
        assertEquals(Genre.OTHER, game.genre)
        assertEquals(Platform.PC, game.platform)
        assertEquals("", game.developer)
        assertEquals("", game.publisher)
        assertEquals("", game.releaseDate)
        assertEquals(0f, game.rating, 0.001f)
        assertEquals(0.0, game.price, 0.001)
        assertFalse(game.isFree)
        assertFalse(game.isFeatured)
        assertEquals(null, game.minimumRequirements)
        assertEquals(null, game.recommendedRequirements)
        assertTrue(game.screenshots.isEmpty())
    }

    @Test
    fun `Game can be created with custom values`() {
        val screenshots = listOf("screenshot1.png", "screenshot2.png")
        val game = Game(
            id = 1L,
            title = "Test Game",
            description = "A test game description",
            thumbnailUrl = "https://example.com/thumb.png",
            genre = Genre.ACTION,
            platform = Platform.PLAYSTATION,
            developer = "Test Developer",
            publisher = "Test Publisher",
            releaseDate = "2024-01-15",
            rating = 4.5f,
            price = 59.99,
            isFree = false,
            isFeatured = true,
            minimumRequirements = "OS: Windows 10",
            recommendedRequirements = "OS: Windows 11",
            screenshots = screenshots
        )

        assertEquals(1L, game.id)
        assertEquals("Test Game", game.title)
        assertEquals("A test game description", game.description)
        assertEquals("https://example.com/thumb.png", game.thumbnailUrl)
        assertEquals(Genre.ACTION, game.genre)
        assertEquals(Platform.PLAYSTATION, game.platform)
        assertEquals("Test Developer", game.developer)
        assertEquals("Test Publisher", game.publisher)
        assertEquals("2024-01-15", game.releaseDate)
        assertEquals(4.5f, game.rating, 0.001f)
        assertEquals(59.99, game.price, 0.001)
        assertFalse(game.isFree)
        assertTrue(game.isFeatured)
        assertEquals("OS: Windows 10", game.minimumRequirements)
        assertEquals("OS: Windows 11", game.recommendedRequirements)
        assertEquals(screenshots, game.screenshots)
    }

    @Test
    fun `Game copy creates modified copy correctly`() {
        val original = Game(
            id = 1L,
            title = "Original Title",
            rating = 3.0f
        )

        val copy = original.copy(title = "New Title", rating = 5.0f)

        assertEquals(1L, copy.id)
        assertEquals("New Title", copy.title)
        assertEquals(5.0f, copy.rating, 0.001f)
        // Original unchanged
        assertEquals("Original Title", original.title)
        assertEquals(3.0f, original.rating, 0.001f)
    }

    @Test
    fun `Game equality works correctly`() {
        val game1 = Game(id = 1L, title = "Game")
        val game2 = Game(id = 1L, title = "Game")
        val game3 = Game(id = 2L, title = "Game")

        assertEquals(game1, game2)
        assertFalse(game1 == game3)
    }

    @Test
    fun `Genre enum has all expected values`() {
        val genres = Genre.entries

        assertEquals(9, genres.size)
        assertTrue(genres.contains(Genre.ACTION))
        assertTrue(genres.contains(Genre.ADVENTURE))
        assertTrue(genres.contains(Genre.RPG))
        assertTrue(genres.contains(Genre.STRATEGY))
        assertTrue(genres.contains(Genre.SIMULATION))
        assertTrue(genres.contains(Genre.SPORTS))
        assertTrue(genres.contains(Genre.PUZZLE))
        assertTrue(genres.contains(Genre.INDIE))
        assertTrue(genres.contains(Genre.OTHER))
    }

    @Test
    fun `Platform enum has all expected values`() {
        val platforms = Platform.entries

        assertEquals(8, platforms.size)
        assertTrue(platforms.contains(Platform.PC))
        assertTrue(platforms.contains(Platform.PLAYSTATION))
        assertTrue(platforms.contains(Platform.XBOX))
        assertTrue(platforms.contains(Platform.NINTENDO))
        assertTrue(platforms.contains(Platform.MOBILE))
        assertTrue(platforms.contains(Platform.WEB))
        assertTrue(platforms.contains(Platform.CROSSPLATFORM))
        assertTrue(platforms.contains(Platform.OTHER))
    }

    @Test
    fun `Free game has isFree true`() {
        val freeGame = Game(
            id = 1L,
            title = "Free Game",
            isFree = true,
            price = 0.0
        )

        assertTrue(freeGame.isFree)
        assertEquals(0.0, freeGame.price, 0.001)
    }

    @Test
    fun `Featured game has isFeatured true`() {
        val featuredGame = Game(
            id = 1L,
            title = "Featured Game",
            isFeatured = true
        )

        assertTrue(featuredGame.isFeatured)
    }

    @Test
    fun `Game with empty screenshots list`() {
        val game = Game(screenshots = emptyList())

        assertTrue(game.screenshots.isEmpty())
        assertEquals(0, game.screenshots.size)
    }

    @Test
    fun `Game with multiple screenshots`() {
        val screenshots = listOf("shot1.png", "shot2.png", "shot3.png")
        val game = Game(screenshots = screenshots)

        assertEquals(3, game.screenshots.size)
        assertEquals("shot1.png", game.screenshots[0])
        assertEquals("shot2.png", game.screenshots[1])
        assertEquals("shot3.png", game.screenshots[2])
    }
}