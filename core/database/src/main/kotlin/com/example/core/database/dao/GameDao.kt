package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.core.database.entity.GameDetailEntity
import com.example.core.database.entity.GameEntity
import com.example.core.database.entity.SearchCacheEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for game-related database operations.
 * Provides methods for CRUD operations on games and game details.
 */
@Dao
interface GameDao {
    
    // ========== Game Operations ==========
    
    /**
     * Inserts a list of games into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)
    
    /**
     * Inserts a single game into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)
    
    /**
     * Updates an existing game
     */
    @Update
    suspend fun updateGame(game: GameEntity)
    
    /**
     * Deletes a game from the database
     */
    @Delete
    suspend fun deleteGame(game: GameEntity)
    
    /**
     * Deletes all games from the database
     */
    @Query("DELETE FROM games")
    suspend fun deleteAllGames()
    
    /**
     * Gets a game by its ID
     */
    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: Long): GameEntity?
    
    /**
     * Gets a game by its ID as Flow for reactive updates
     */
    @Query("SELECT * FROM games WHERE id = :gameId")
    fun observeGameById(gameId: Long): Flow<GameEntity?>
    
    /**
     * Gets all games from the database
     */
    @Query("SELECT * FROM games ORDER BY rating DESC")
    suspend fun getAllGames(): List<GameEntity>
    
    /**
     * Gets all games as Flow for reactive updates
     */
    @Query("SELECT * FROM games ORDER BY rating DESC")
    fun observeAllGames(): Flow<List<GameEntity>>
    
    /**
     * Gets featured games
     */
    @Query("SELECT * FROM games WHERE isFeatured = 1 ORDER BY rating DESC")
    suspend fun getFeaturedGames(): List<GameEntity>
    
    /**
     * Gets featured games as Flow
     */
    @Query("SELECT * FROM games WHERE isFeatured = 1 ORDER BY rating DESC")
    fun observeFeaturedGames(): Flow<List<GameEntity>>
    
    /**
     * Gets games by genre
     */
    @Query("SELECT * FROM games WHERE genre = :genre ORDER BY rating DESC")
    suspend fun getGamesByGenre(genre: String): List<GameEntity>
    
    /**
     * Gets games by genre as Flow
     */
    @Query("SELECT * FROM games WHERE genre = :genre ORDER BY rating DESC")
    fun observeGamesByGenre(genre: String): Flow<List<GameEntity>>
    
    /**
     * Gets games by platform
     */
    @Query("SELECT * FROM games WHERE platform = :platform ORDER BY rating DESC")
    suspend fun getGamesByPlatform(platform: String): List<GameEntity>
    
    /**
     * Gets games by platform as Flow
     */
    @Query("SELECT * FROM games WHERE platform = :platform ORDER BY rating DESC")
    fun observeGamesByPlatform(platform: String): Flow<List<GameEntity>>
    
    /**
     * Searches games by title (case-insensitive)
     */
    @Query("SELECT * FROM games WHERE title LIKE '%' || :query || '%' ORDER BY rating DESC")
    suspend fun searchGamesByTitle(query: String): List<GameEntity>
    
    /**
     * Searches games by title as Flow
     */
    @Query("SELECT * FROM games WHERE title LIKE '%' || :query || '%' ORDER BY rating DESC")
    fun observeSearchGamesByTitle(query: String): Flow<List<GameEntity>>
    
    /**
     * Gets games with rating greater than or equal to specified value
     */
    @Query("SELECT * FROM games WHERE rating >= :minRating ORDER BY rating DESC")
    suspend fun getGamesByMinRating(minRating: Float): List<GameEntity>
    
    /**
     * Gets the count of all games
     */
    @Query("SELECT COUNT(*) FROM games")
    suspend fun getGameCount(): Int
    
    /**
     * Clears games older than specified timestamp
     */
    @Query("DELETE FROM games WHERE cachedAt < :timestamp")
    suspend fun clearOldGames(timestamp: Long)
    
    // ========== Game Detail Operations ==========
    
    /**
     * Inserts or replaces game detail
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameDetail(detail: GameDetailEntity)
    
    /**
     * Gets game detail by game ID
     */
    @Query("SELECT * FROM game_details WHERE gameId = :gameId")
    suspend fun getGameDetailById(gameId: Long): GameDetailEntity?
    
    /**
     * Gets game detail by game ID as Flow
     */
    @Query("SELECT * FROM game_details WHERE gameId = :gameId")
    fun observeGameDetailById(gameId: Long): Flow<GameDetailEntity?>
    
    /**
     * Deletes game detail by game ID
     */
    @Query("DELETE FROM game_details WHERE gameId = :gameId")
    suspend fun deleteGameDetail(gameId: Long)
    
    /**
     * Deletes all game details
     */
    @Query("DELETE FROM game_details")
    suspend fun deleteAllGameDetails()
    
    // ========== Search Cache Operations ==========
    
    /**
     * Saves search cache entry
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCache(cache: SearchCacheEntity)
    
    /**
     * Gets search cache entry by query
     */
    @Query("SELECT * FROM search_cache WHERE query = :query AND expiresAt > :currentTime")
    suspend fun getSearchCache(query: String, currentTime: Long = System.currentTimeMillis()): SearchCacheEntity?
    
    /**
     * Clears expired search cache entries
     */
    @Query("DELETE FROM search_cache WHERE expiresAt < :currentTime")
    suspend fun clearExpiredSearchCache(currentTime: Long = System.currentTimeMillis())
    
    /**
     * Clears all search cache
     */
    @Query("DELETE FROM search_cache")
    suspend fun clearAllSearchCache()
}