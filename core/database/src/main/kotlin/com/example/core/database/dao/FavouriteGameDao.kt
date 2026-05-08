package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.FavouriteGameEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for favourite game operations.
 * Provides methods for managing device-local favourites without user accounts.
 */
@Dao
interface FavouriteGameDao {
    
    /**
     * Inserts a favourite game into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: FavouriteGameEntity)
    
    /**
     * Deletes a favourite game from the database
     */
    @Delete
    suspend fun delete(favourite: FavouriteGameEntity)
    
    /**
     * Gets all favourite games from the database
     */
    @Query("SELECT * FROM favourite_games ORDER BY savedAt DESC")
    fun getAllFavourites(): Flow<List<FavouriteGameEntity>>

    /**
     * Gets all favourite games from the database (suspend version)
     */
    @Query("SELECT * FROM favourite_games ORDER BY savedAt DESC")
    suspend fun getAllFavouritesList(): List<FavouriteGameEntity>
    
    /**
     * Checks if a game is favourited
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favourite_games WHERE gameId = :gameId)")
    fun isFavourite(gameId: Long): Flow<Boolean>
    
    /**
     * Gets a favourite game by game ID
     */
    @Query("SELECT * FROM favourite_games WHERE gameId = :gameId")
    suspend fun getFavouriteByGameId(gameId: Long): FavouriteGameEntity?
    
    /**
     * Gets a favourite game by game ID as Flow
     */
    @Query("SELECT * FROM favourite_games WHERE gameId = :gameId")
    fun observeFavouriteByGameId(gameId: Long): Flow<FavouriteGameEntity?>
    
    /**
     * Deletes a favourite game by game ID
     */
    @Query("DELETE FROM favourite_games WHERE gameId = :gameId")
    suspend fun deleteByGameId(gameId: Long)
    
    /**
     * Gets the count of all favourite games
     */
    @Query("SELECT COUNT(*) FROM favourite_games")
    suspend fun getFavouriteCount(): Int
    
    /**
     * Gets the count of all favourite games as Flow
     */
    @Query("SELECT COUNT(*) FROM favourite_games")
    fun observeFavouriteCount(): Flow<Int>
    
    /**
     * Deletes all favourite games
     */
    @Query("DELETE FROM favourite_games")
    suspend fun deleteAllFavourites()
    
    /**
     * Clears favourites older than specified timestamp
     */
    @Query("DELETE FROM favourite_games WHERE savedAt < :timestamp")
    suspend fun clearOldFavourites(timestamp: Long)
}