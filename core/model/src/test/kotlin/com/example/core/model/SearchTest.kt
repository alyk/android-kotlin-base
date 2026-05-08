package com.example.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for SearchFilter and SearchResult classes.
 */
class SearchTest {

    @Test
    fun `SearchFilter has correct default values`() {
        val filter = SearchFilter()

        assertEquals("", filter.query)
        assertTrue(filter.genres.isEmpty())
        assertTrue(filter.platforms.isEmpty())
        assertNull(filter.minRating)
        assertNull(filter.releaseYear)
        assertEquals(SortOption.RELEVANCE, filter.sortBy)
    }

    @Test
    fun `SearchFilter can be created with custom values`() {
        val genres = listOf(Genre.ACTION, Genre.ADVENTURE)
        val platforms = listOf(Platform.PC, Platform.PLAYSTATION)

        val filter = SearchFilter(
            query = "test game",
            genres = genres,
            platforms = platforms,
            minRating = 4.0f,
            releaseYear = 2024,
            sortBy = SortOption.RATING
        )

        assertEquals("test game", filter.query)
        assertEquals(genres, filter.genres)
        assertEquals(platforms, filter.platforms)
        assertEquals(4.0f, filter.minRating)
        assertEquals(2024, filter.releaseYear)
        assertEquals(SortOption.RATING, filter.sortBy)
    }

    @Test
    fun `SearchFilter copy creates modified copy correctly`() {
        val original = SearchFilter(query = "original")
        val copy = original.copy(query = "modified", sortBy = SortOption.ALPHABETICAL)

        assertEquals("modified", copy.query)
        assertEquals(SortOption.ALPHABETICAL, copy.sortBy)
        // Original unchanged
        assertEquals("original", original.query)
        assertEquals(SortOption.RELEVANCE, original.sortBy)
    }

    @Test
    fun `SearchResult has correct values`() {
        val games = listOf(
            Game(id = 1, title = "Game 1"),
            Game(id = 2, title = "Game 2")
        )

        val result = SearchResult(
            games = games,
            totalCount = 100,
            page = 2,
            pageSize = 20,
            hasMore = true
        )

        assertEquals(2, result.games.size)
        assertEquals(100, result.totalCount)
        assertEquals(2, result.page)
        assertEquals(20, result.pageSize)
        assertTrue(result.hasMore)
    }

    @Test
    fun `SearchResult can determine hasMore correctly`() {
        val allGames = (1..50).map { Game(id = it.toLong(), title = "Game $it") }

        // Last page
        val lastPageResult = SearchResult(
            games = allGames.take(10),
            totalCount = 50,
            page = 5,
            pageSize = 10,
            hasMore = false
        )

        assertFalse(lastPageResult.hasMore)

        // More pages available
        val morePagesResult = SearchResult(
            games = allGames.take(10),
            totalCount = 50,
            page = 4,
            pageSize = 10,
            hasMore = true
        )

        assertTrue(morePagesResult.hasMore)
    }

    @Test
    fun `SortOption has correct display names`() {
        assertEquals("Relevance", SortOption.RELEVANCE.displayName)
        assertEquals("Rating", SortOption.RATING.displayName)
        assertEquals("Release Date", SortOption.RELEASE_DATE.displayName)
        assertEquals("Alphabetical", SortOption.ALPHABETICAL.displayName)
    }

    @Test
    fun `SortOption has all expected values`() {
        val options = SortOption.entries

        assertEquals(4, options.size)
        assertTrue(options.contains(SortOption.RELEVANCE))
        assertTrue(options.contains(SortOption.RATING))
        assertTrue(options.contains(SortOption.RELEASE_DATE))
        assertTrue(options.contains(SortOption.ALPHABETICAL))
    }

    @Test
    fun `SearchFilter with multiple genres`() {
        val filter = SearchFilter(
            genres = listOf(Genre.ACTION, Genre.RPG, Genre.INDIE)
        )

        assertEquals(3, filter.genres.size)
        assertTrue(filter.genres.contains(Genre.ACTION))
        assertTrue(filter.genres.contains(Genre.RPG))
        assertTrue(filter.genres.contains(Genre.INDIE))
    }

    @Test
    fun `SearchFilter with multiple platforms`() {
        val filter = SearchFilter(
            platforms = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX)
        )

        assertEquals(3, filter.platforms.size)
        assertTrue(filter.platforms.contains(Platform.PC))
        assertTrue(filter.platforms.contains(Platform.PLAYSTATION))
        assertTrue(filter.platforms.contains(Platform.XBOX))
    }

    @Test
    fun `SearchResult empty games list`() {
        val result = SearchResult(
            games = emptyList(),
            totalCount = 0,
            page = 1,
            pageSize = 20,
            hasMore = false
        )

        assertTrue(result.games.isEmpty())
        assertEquals(0, result.totalCount)
    }

    @Test
    fun `SearchResult with single page`() {
        val games = listOf(Game(id = 1, title = "Single Game"))

        val result = SearchResult(
            games = games,
            totalCount = 1,
            page = 1,
            pageSize = 20,
            hasMore = false
        )

        assertEquals(1, result.games.size)
        assertFalse(result.hasMore)
    }
}