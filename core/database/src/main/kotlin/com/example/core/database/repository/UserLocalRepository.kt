package com.example.core.database.repository

import com.example.core.database.dao.UserDao
import com.example.core.database.entity.FavouriteEntity
import com.example.core.database.entity.FavouriteWithGameEntity
import com.example.core.database.entity.UserEntity
import com.example.core.model.Favourite
import com.example.core.model.FavouriteWithGame as DomainFavouriteWithGame
import com.example.core.model.Result
import com.example.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for user-related data operations.
 */
interface UserLocalRepository {
    suspend fun getCurrentUser(): Result<User?>
    suspend fun saveUser(user: User)
    suspend fun updateUserPreferences(userId: Long, preferences: com.example.core.model.UserPreferences)
    suspend fun deleteUser(userId: Long)
    
    // Favourite operations
    suspend fun addFavourite(userId: Long, gameId: Long): Result<Long>
    suspend fun removeFavourite(userId: Long, gameId: Long): Result<Unit>
    suspend fun getFavourites(userId: Long): Result<List<Favourite>>
    suspend fun getFavouritesWithGames(userId: Long): Result<List<DomainFavouriteWithGame>>
    suspend fun isFavourited(userId: Long, gameId: Long): Result<Boolean>
    suspend fun getFavouriteCount(userId: Long): Result<Int>
    
    // Flow-based reactive methods
    fun observeCurrentUser(): Flow<User?>
    fun observeFavourites(userId: Long): Flow<List<Favourite>>
    fun observeFavouritesWithGames(userId: Long): Flow<List<DomainFavouriteWithGame>>
    fun observeIsFavourited(userId: Long, gameId: Long): Flow<Boolean>
    fun observeFavouriteCount(userId: Long): Flow<Int>
}

/**
 * Implementation of UserLocalRepository using Room database.
 */
class UserLocalRepositoryImpl(
    private val userDao: UserDao
) : UserLocalRepository {
    
    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            // For simplicity, return the first user or null
            val users = userDao.getAllUsers()
            val user = users.firstOrNull()?.toDomain()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error("Failed to fetch current user: ${e.message}", e)
        }
    }
    
    override suspend fun saveUser(user: User) {
        userDao.insertUser(UserEntity.fromDomain(user))
    }
    
    override suspend fun updateUserPreferences(
        userId: Long, 
        preferences: com.example.core.model.UserPreferences
    ) {
        val existingUser = userDao.getUserById(userId) ?: return
        val updatedUser = existingUser.copy(
            favouriteGenres = preferences.favouriteGenres.joinToString(",") { it.name },
            favouritePlatforms = preferences.favouritePlatforms.joinToString(",") { it.name },
            notificationsEnabled = preferences.notificationsEnabled,
            darkModeEnabled = preferences.darkModeEnabled
        )
        userDao.updateUser(updatedUser)
    }
    
    override suspend fun deleteUser(userId: Long) {
        val user = userDao.getUserById(userId) ?: return
        userDao.deleteUser(user)
    }
    
    override suspend fun addFavourite(userId: Long, gameId: Long): Result<Long> {
        return try {
            // Check if already favourited
            if (userDao.isFavourited(userId, gameId)) {
                return Result.Error("Game is already in favourites")
            }
            
            val favourite = FavouriteEntity(
                userId = userId,
                gameId = gameId,
                addedAt = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
                    .format(java.util.Date())
            )
            val id = userDao.insertFavourite(favourite)
            Result.Success(id)
        } catch (e: Exception) {
            Result.Error("Failed to add favourite: ${e.message}", e)
        }
    }
    
    override suspend fun removeFavourite(userId: Long, gameId: Long): Result<Unit> {
        return try {
            userDao.deleteFavouriteByIds(userId, gameId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to remove favourite: ${e.message}", e)
        }
    }
    
    override suspend fun getFavourites(userId: Long): Result<List<Favourite>> {
        return try {
            val favourites = userDao.getFavouritesByUser(userId)
            Result.Success(favourites.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to fetch favourites: ${e.message}", e)
        }
    }
    
    override suspend fun getFavouritesWithGames(userId: Long): Result<List<DomainFavouriteWithGame>> {
        return try {
            val favourites = userDao.getFavouritesWithGamesByUser(userId)
            val domainFavourites = favourites.map { entity ->
                DomainFavouriteWithGame(
                    favourite = Favourite(
                        id = entity.favouriteId,
                        userId = entity.userId,
                        gameId = entity.gameId,
                        addedAt = entity.addedAt
                    ),
                    game = com.example.core.model.Game(
                        id = entity.gameId,
                        title = entity.gameTitle,
                        description = entity.gameDescription,
                        thumbnailUrl = entity.gameThumbnailUrl,
                        genre = try {
                            com.example.core.model.Genre.valueOf(entity.gameGenre)
                        } catch (e: Exception) {
                            com.example.core.model.Genre.OTHER
                        },
                        platform = try {
                            com.example.core.model.Platform.valueOf(entity.gamePlatform)
                        } catch (e: Exception) {
                            com.example.core.model.Platform.CROSSPLATFORM
                        },
                        releaseDate = entity.gameReleaseDate,
                        rating = entity.gameRating.coerceIn(0f, 5f),
                        developer = entity.gameDeveloper,
                        publisher = entity.gamePublisher,
                        isFeatured = entity.gameIsFeatured
                    )
                )
            }
            Result.Success(domainFavourites)
        } catch (e: Exception) {
            Result.Error("Failed to fetch favourites with games: ${e.message}", e)
        }
    }
    
    override suspend fun isFavourited(userId: Long, gameId: Long): Result<Boolean> {
        return try {
            val isFavourited = userDao.isFavourited(userId, gameId)
            Result.Success(isFavourited)
        } catch (e: Exception) {
            Result.Error("Failed to check favourite status: ${e.message}", e)
        }
    }
    
    override suspend fun getFavouriteCount(userId: Long): Result<Int> {
        return try {
            val count = userDao.getFavouriteCount(userId)
            Result.Success(count)
        } catch (e: Exception) {
            Result.Error("Failed to get favourite count: ${e.message}", e)
        }
    }
    
    // Reactive Flow methods
    override fun observeCurrentUser(): Flow<User?> {
        return userDao.observeAllUsers().map { users ->
            users.firstOrNull()?.toDomain()
        }
    }
    
    override fun observeFavourites(userId: Long): Flow<List<Favourite>> {
        return userDao.observeFavouritesByUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun observeFavouritesWithGames(userId: Long): Flow<List<DomainFavouriteWithGame>> {
        return userDao.observeFavouritesWithGamesByUser(userId).map { entities ->
            entities.map { entity ->
                DomainFavouriteWithGame(
                    favourite = Favourite(
                        id = entity.favouriteId,
                        userId = entity.userId,
                        gameId = entity.gameId,
                        addedAt = entity.addedAt
                    ),
                    game = com.example.core.model.Game(
                        id = entity.gameId,
                        title = entity.gameTitle,
                        description = entity.gameDescription,
                        thumbnailUrl = entity.gameThumbnailUrl,
                        genre = try {
                            com.example.core.model.Genre.valueOf(entity.gameGenre)
                        } catch (e: Exception) {
                            com.example.core.model.Genre.OTHER
                        },
                        platform = try {
                            com.example.core.model.Platform.valueOf(entity.gamePlatform)
                        } catch (e: Exception) {
                            com.example.core.model.Platform.CROSSPLATFORM
                        },
                        releaseDate = entity.gameReleaseDate,
                        rating = entity.gameRating.coerceIn(0f, 5f),
                        developer = entity.gameDeveloper,
                        publisher = entity.gamePublisher,
                        isFeatured = entity.gameIsFeatured
                    )
                )
            }
        }
    }
    
    override fun observeIsFavourited(userId: Long, gameId: Long): Flow<Boolean> {
        return userDao.observeIsFavourited(userId, gameId)
    }
    
    override fun observeFavouriteCount(userId: Long): Flow<Int> {
        return userDao.observeFavouriteCount(userId)
    }
}