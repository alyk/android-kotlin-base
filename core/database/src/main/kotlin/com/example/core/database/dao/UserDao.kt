package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.database.entity.FavouriteEntity
import com.example.core.database.entity.FavouriteWithGameEntity
import com.example.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for user-related database operations.
 * Provides methods for CRUD operations on users and favourites.
 */
@Dao
interface UserDao {
    
    // ========== User Operations ==========
    
    /**
     * Inserts a user into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    /**
     * Updates an existing user
     */
    @Update
    suspend fun updateUser(user: UserEntity)
    
    /**
     * Deletes a user from the database
     */
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    /**
     * Gets a user by their ID
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?
    
    /**
     * Gets a user by their ID as Flow for reactive updates
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUserById(userId: Long): Flow<UserEntity?>
    
    /**
     * Gets a user by their email
     */
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    
    /**
     * Gets all users
     */
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
    
    /**
     * Gets all users as Flow
     */
    @Query("SELECT * FROM users")
    fun observeAllUsers(): Flow<List<UserEntity>>
    
    /**
     * Gets the count of all users
     */
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
    
    /**
     * Deletes all users
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
    
    // ========== Favourite Operations ==========
    
    /**
     * Inserts a favourite into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity): Long
    
    /**
     * Deletes a favourite from the database
     */
    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity)
    
    /**
     * Deletes a favourite by user ID and game ID
     */
    @Query("DELETE FROM favourites WHERE userId = :userId AND gameId = :gameId")
    suspend fun deleteFavouriteByIds(userId: Long, gameId: Long)
    
    /**
     * Gets all favourites for a user
     */
    @Query("SELECT * FROM favourites WHERE userId = :userId ORDER BY addedAt DESC")
    suspend fun getFavouritesByUser(userId: Long): List<FavouriteEntity>
    
    /**
     * Gets all favourites for a user as Flow
     */
    @Query("SELECT * FROM favourites WHERE userId = :userId ORDER BY addedAt DESC")
    fun observeFavouritesByUser(userId: Long): Flow<List<FavouriteEntity>>
    
    /**
     * Gets favourite with full game details for a user
     */
    @Query("""
        SELECT 
            f.id as favouriteId,
            f.userId,
            f.gameId,
            f.addedAt,
            g.title as gameTitle,
            g.description as gameDescription,
            g.thumbnailUrl as gameThumbnailUrl,
            g.genre as gameGenre,
            g.platform as gamePlatform,
            g.releaseDate as gameReleaseDate,
            g.rating as gameRating,
            g.developer as gameDeveloper,
            g.publisher as gamePublisher,
            g.isFeatured as gameIsFeatured
        FROM favourites f
        INNER JOIN games g ON f.gameId = g.id
        WHERE f.userId = :userId
        ORDER BY f.addedAt DESC
    """)
    suspend fun getFavouritesWithGamesByUser(userId: Long): List<FavouriteWithGameEntity>
    
    /**
     * Gets favourite with full game details as Flow
     */
    @Query("""
        SELECT 
            f.id as favouriteId,
            f.userId,
            f.gameId,
            f.addedAt,
            g.title as gameTitle,
            g.description as gameDescription,
            g.thumbnailUrl as gameThumbnailUrl,
            g.genre as gameGenre,
            g.platform as gamePlatform,
            g.releaseDate as gameReleaseDate,
            g.rating as gameRating,
            g.developer as gameDeveloper,
            g.publisher as gamePublisher,
            g.isFeatured as gameIsFeatured
        FROM favourites f
        INNER JOIN games g ON f.gameId = g.id
        WHERE f.userId = :userId
        ORDER BY f.addedAt DESC
    """)
    fun observeFavouritesWithGamesByUser(userId: Long): Flow<List<FavouriteWithGameEntity>>
    
    /**
     * Checks if a game is favourited by a user
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = :userId AND gameId = :gameId)")
    suspend fun isFavourited(userId: Long, gameId: Long): Boolean
    
    /**
     * Checks if a game is favourited as Flow
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = :userId AND gameId = :gameId)")
    fun observeIsFavourited(userId: Long, gameId: Long): Flow<Boolean>
    
    /**
     * Gets the count of favourites for a user
     */
    @Query("SELECT COUNT(*) FROM favourites WHERE userId = :userId")
    suspend fun getFavouriteCount(userId: Long): Int
    
    /**
     * Gets the count of favourites for a user as Flow
     */
    @Query("SELECT COUNT(*) FROM favourites WHERE userId = :userId")
    fun observeFavouriteCount(userId: Long): Flow<Int>
    
    /**
     * Deletes all favourites for a user
     */
    @Query("DELETE FROM favourites WHERE userId = :userId")
    suspend fun deleteAllFavouritesForUser(userId: Long)
    
    /**
     * Deletes all favourites
     */
    @Query("DELETE FROM favourites")
    suspend fun deleteAllFavourites()
}