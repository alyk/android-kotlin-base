package com.example.core.data.datasource

import com.example.core.data.network.ApiResponse
import com.example.core.data.network.GameApiService
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.SearchResult
import com.example.core.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Remote data source for fetching game data from the API.
 * Handles all network communication related to games.
 */
class GameRemoteDataSource(
    private val apiService: GameApiService = NetworkClient.apiService
) {
    /**
     * Fetches a paginated list of games
     */
    suspend fun getGames(page: Int = 1, pageSize: Int = 20): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGames(page, pageSize)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch games")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
    
    /**
     * Fetches featured games for display on home screen
     */
    suspend fun getFeaturedGames(): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getFeaturedGames()
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch featured games")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
    
    /**
     * Fetches a single game by its ID
     */
    suspend fun getGameById(gameId: Long): Result<GameDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGameById(gameId)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Game not found")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
    
    /**
     * Searches for games based on filter criteria
     */
    suspend fun searchGames(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Result<SearchResult> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchGames(query = query, page = page, pageSize = pageSize)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Search failed")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
    
    /**
     * Fetches games filtered by genre
     */
    suspend fun getGamesByGenre(
        genre: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGamesByGenre(genre, page, pageSize)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch games by genre")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
    
    /**
     * Fetches games filtered by platform
     */
    suspend fun getGamesByPlatform(
        platform: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGamesByPlatform(platform, page, pageSize)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch games by platform")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred", e)
            }
        }
    }
}