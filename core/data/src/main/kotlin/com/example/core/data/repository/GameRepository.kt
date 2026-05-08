package com.example.core.data.repository

import com.example.core.data.datasource.GameRemoteDataSource
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Result
import com.example.core.model.SearchResult

/**
 * Repository interface for game data operations.
 * Abstracts the data source from the rest of the application.
 */
interface GameRepository {
    suspend fun getGames(page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getFeaturedGames(): Result<List<Game>>
    suspend fun getGameById(gameId: Long): Result<GameDetail>
    suspend fun searchGames(query: String, page: Int = 1, pageSize: Int = 20): Result<SearchResult>
    suspend fun getGamesByGenre(genre: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getGamesByPlatform(platform: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
}

/**
 * Default implementation of GameRepository.
 * Uses remote data source for all operations.
 */
class GameRepositoryImpl(
    private val remoteDataSource: GameRemoteDataSource = GameRemoteDataSource()
) : GameRepository {
    
    override suspend fun getGames(page: Int, pageSize: Int): Result<List<Game>> {
        return remoteDataSource.getGames(page, pageSize)
    }
    
    override suspend fun getFeaturedGames(): Result<List<Game>> {
        return remoteDataSource.getFeaturedGames()
    }
    
    override suspend fun getGameById(gameId: Long): Result<GameDetail> {
        return remoteDataSource.getGameById(gameId)
    }
    
    override suspend fun searchGames(query: String, page: Int, pageSize: Int): Result<SearchResult> {
        return remoteDataSource.searchGames(query, page, pageSize)
    }
    
    override suspend fun getGamesByGenre(genre: String, page: Int, pageSize: Int): Result<List<Game>> {
        return remoteDataSource.getGamesByGenre(genre, page, pageSize)
    }
    
    override suspend fun getGamesByPlatform(platform: String, page: Int, pageSize: Int): Result<List<Game>> {
        return remoteDataSource.getGamesByPlatform(platform, page, pageSize)
    }
}