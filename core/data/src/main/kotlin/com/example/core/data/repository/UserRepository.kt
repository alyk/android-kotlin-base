package com.example.core.data.repository

import com.example.core.database.repository.UserLocalRepository
import com.example.core.model.Game
import com.example.core.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for user-related data operations.
 * Abstracts user data sources from the rest of the application.
 */
interface UserRepository {
    /**
     * Gets favourite games for the current user
     */
    fun getFavourites(): Flow<List<Game>>
    
    /**
     * Adds a game to favourites
     */
    suspend fun addFavourite(gameId: Long): Result<Unit>
    
    /**
     * Removes a game from favourites
     */
    suspend fun removeFavourite(gameId: Long): Result<Unit>
    
    /**
     * Checks if a game is in favourites
     */
    suspend fun isFavourited(gameId: Long): Boolean
}

/**
 * Default implementation of UserRepository.
 * Uses local database for user data operations.
 */
class UserRepositoryImpl(
    private val userLocalRepository: UserLocalRepository
) : UserRepository {
    
    companion object {
        // For simplicity, use user ID 1 as the current user
        private const val CURRENT_USER_ID = 1L
    }
    
    override fun getFavourites(): Flow<List<Game>> {
        return userLocalRepository.observeFavouritesWithGames(CURRENT_USER_ID).map { favourites ->
            favourites.map { it.game }
        }
    }
    
    override suspend fun addFavourite(gameId: Long): Result<Unit> {
        return userLocalRepository.addFavourite(CURRENT_USER_ID, gameId).map { }
    }
    
    override suspend fun removeFavourite(gameId: Long): Result<Unit> {
        return userLocalRepository.removeFavourite(CURRENT_USER_ID, gameId)
    }
    
    override suspend fun isFavourited(gameId: Long): Boolean {
        val result = userLocalRepository.isFavourited(CURRENT_USER_ID, gameId)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> false
            is Result.Loading -> false
        }
    }
}