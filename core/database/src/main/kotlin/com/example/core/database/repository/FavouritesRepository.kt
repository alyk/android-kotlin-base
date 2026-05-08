package com.example.core.database.repository

import com.example.core.database.dao.FavouriteGameDao
import com.example.core.database.entity.FavouriteGameEntity
import com.example.core.model.LocalFavourite
import com.example.core.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension function to convert FavouriteGameEntity to LocalFavourite domain model
 */
private fun FavouriteGameEntity.toDomain(): LocalFavourite {
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

/**
 * Extension function to convert LocalFavourite domain model to FavouriteGameEntity
 */
private fun LocalFavourite.toEntity(): FavouriteGameEntity {
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

/**
 * Repository interface for device-local favourites operations.
 * Abstracts the data source from the rest of the application.
 */
interface FavouritesRepository {
    suspend fun addFavourite(favourite: LocalFavourite): Result<Unit>
    suspend fun removeFavourite(gameId: Long): Result<Unit>
    suspend fun removeFavourite(favourite: LocalFavourite): Result<Unit>
    suspend fun getFavouriteByGameId(gameId: Long): Result<LocalFavourite?>
    suspend fun getAllFavourites(): Result<List<LocalFavourite>>
    suspend fun isFavourite(gameId: Long): Result<Boolean>
    suspend fun getFavouriteCount(): Result<Int>
    suspend fun clearAllFavourites(): Result<Unit>
    suspend fun clearOldFavourites(timestamp: Long): Result<Unit>
    
    // Flow-based reactive methods
    fun observeAllFavourites(): Flow<List<LocalFavourite>>
    fun observeFavouriteByGameId(gameId: Long): Flow<LocalFavourite?>
    fun observeIsFavourite(gameId: Long): Flow<Boolean>
    fun observeFavouriteCount(): Flow<Int>
}

/**
 * Implementation of FavouritesRepository using Room database.
 */
class FavouritesRepositoryImpl(
    private val favouriteGameDao: FavouriteGameDao
) : FavouritesRepository {
    
    override suspend fun addFavourite(favourite: LocalFavourite): Result<Unit> {
        return try {
            favouriteGameDao.insert(favourite.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to add favourite: ${e.message}")
        }
    }
    
    override suspend fun removeFavourite(gameId: Long): Result<Unit> {
        return try {
            favouriteGameDao.deleteByGameId(gameId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to remove favourite: ${e.message}")
        }
    }
    
    override suspend fun removeFavourite(favourite: LocalFavourite): Result<Unit> {
        return try {
            favouriteGameDao.delete(favourite.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to remove favourite: ${e.message}")
        }
    }
    
    override suspend fun getFavouriteByGameId(gameId: Long): Result<LocalFavourite?> {
        return try {
            val favourite = favouriteGameDao.getFavouriteByGameId(gameId)
            Result.Success(favourite?.toDomain())
        } catch (e: Exception) {
            Result.Error("Failed to get favourite: ${e.message}")
        }
    }
    
    override suspend fun getAllFavourites(): Result<List<LocalFavourite>> {
        return try {
            val favourites = favouriteGameDao.getAllFavouritesList()
            Result.Success(favourites.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to get favourites: ${e.message}")
        }
    }
    
    override suspend fun isFavourite(gameId: Long): Result<Boolean> {
        return try {
            val favourite = favouriteGameDao.getFavouriteByGameId(gameId)
            Result.Success(favourite != null)
        } catch (e: Exception) {
            Result.Error("Failed to check favourite status: ${e.message}")
        }
    }
    
    override suspend fun getFavouriteCount(): Result<Int> {
        return try {
            val count = favouriteGameDao.getFavouriteCount()
            Result.Success(count)
        } catch (e: Exception) {
            Result.Error("Failed to get favourite count: ${e.message}")
        }
    }
    
    override suspend fun clearAllFavourites(): Result<Unit> {
        return try {
            favouriteGameDao.deleteAllFavourites()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to clear favourites: ${e.message}")
        }
    }
    
    override suspend fun clearOldFavourites(timestamp: Long): Result<Unit> {
        return try {
            favouriteGameDao.clearOldFavourites(timestamp)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Failed to clear old favourites: ${e.message}")
        }
    }
    
    // Reactive Flow methods
    override fun observeAllFavourites(): Flow<List<LocalFavourite>> {
        return favouriteGameDao.getAllFavourites().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun observeFavouriteByGameId(gameId: Long): Flow<LocalFavourite?> {
        return favouriteGameDao.observeFavouriteByGameId(gameId).map { it?.toDomain() }
    }
    
    override fun observeIsFavourite(gameId: Long): Flow<Boolean> {
        return favouriteGameDao.isFavourite(gameId)
    }
    
    override fun observeFavouriteCount(): Flow<Int> {
        return favouriteGameDao.observeFavouriteCount()
    }
}