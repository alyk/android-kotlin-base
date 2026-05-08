package com.example.core.data.datasource

import com.example.core.data.network.GameApiService
import com.example.core.data.network.NetworkClient
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.SearchResult
import com.example.core.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Interface for game data sources.
 * Allows switching between real and mock implementations.
 */
interface GameDataSource {
    suspend fun getGames(page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getFeaturedGames(): Result<List<Game>>
    suspend fun getGameById(gameId: Long): Result<GameDetail>
    suspend fun searchGames(query: String, page: Int = 1, pageSize: Int = 20): Result<SearchResult>
    suspend fun getGamesByGenre(genre: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getGamesByPlatform(platform: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
}

/**
 * Remote data source for fetching game data from the API.
 * Handles all network communication related to games.
 */
class GameRemoteDataSource(
    private val apiService: GameApiService = NetworkClient.apiService
) : GameDataSource {
    
    override suspend fun getGames(page: Int, pageSize: Int): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGames(page, pageSize)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch games")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }

    override suspend fun getFeaturedGames(): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getFeaturedGames()
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Failed to fetch featured games")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }

    override suspend fun getGameById(gameId: Long): Result<GameDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGameById(gameId)
                if (response.success && response.data != null) {
                    Result.Success(response.data)
                } else {
                    Result.Error(response.message ?: "Game not found")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }

    override suspend fun searchGames(
        query: String,
        page: Int,
        pageSize: Int
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
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }

    override suspend fun getGamesByGenre(
        genre: String,
        page: Int,
        pageSize: Int
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
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }

    override suspend fun getGamesByPlatform(
        platform: String,
        page: Int,
        pageSize: Int
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
                Result.Error(e.message ?: "Network error occurred")
            }
        }
    }
}