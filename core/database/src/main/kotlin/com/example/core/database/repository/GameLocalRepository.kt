package com.example.core.database.repository

import com.example.core.database.dao.GameDao
import com.example.core.database.entity.GameDetailEntity
import com.example.core.database.entity.GameEntity
import com.example.core.database.entity.SearchCacheEntity
import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for game data operations.
 * Abstracts the data source from the rest of the application.
 */
interface GameLocalRepository {
    suspend fun getGames(page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getFeaturedGames(): Result<List<Game>>
    suspend fun getGameById(gameId: Long): Result<Game?>
    suspend fun getGamesByGenre(genre: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun getGamesByPlatform(platform: String, page: Int = 1, pageSize: Int = 20): Result<List<Game>>
    suspend fun searchGamesLocally(query: String): Result<List<Game>>
    suspend fun saveGames(games: List<Game>)
    suspend fun saveGame(game: Game)
    suspend fun saveGameDetail(gameId: Long, detail: GameDetail)
    suspend fun getGameDetail(gameId: Long): Result<GameDetail?>
    suspend fun clearCache()
    
    // Flow-based reactive methods
    fun observeGames(): Flow<List<Game>>
    fun observeGameById(gameId: Long): Flow<Game?>
    fun observeFeaturedGames(): Flow<List<Game>>
}

/**
 * Implementation of GameLocalRepository using Room database.
 */
class GameLocalRepositoryImpl(
    private val gameDao: GameDao
) : GameLocalRepository {
    
    override suspend fun getGames(page: Int, pageSize: Int): Result<List<Game>> {
        return try {
            val offset = (page - 1) * pageSize
            val games = gameDao.getAllGames().drop(offset).take(pageSize)
            Result.Success(games.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to fetch games: ${e.message}")
        }
    }
    
    override suspend fun getFeaturedGames(): Result<List<Game>> {
        return try {
            val games = gameDao.getFeaturedGames()
            Result.Success(games.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to fetch featured games: ${e.message}")
        }
    }
    
    override suspend fun getGameById(gameId: Long): Result<Game?> {
        return try {
            val game = gameDao.getGameById(gameId)
            Result.Success(game?.toDomain())
        } catch (e: Exception) {
            Result.Error("Failed to fetch game: ${e.message}")
        }
    }
    
    override suspend fun getGamesByGenre(genre: String, page: Int, pageSize: Int): Result<List<Game>> {
        return try {
            val offset = (page - 1) * pageSize
            val games = gameDao.getGamesByGenre(genre).drop(offset).take(pageSize)
            Result.Success(games.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to fetch games by genre: ${e.message}")
        }
    }
    
    override suspend fun getGamesByPlatform(platform: String, page: Int, pageSize: Int): Result<List<Game>> {
        return try {
            val offset = (page - 1) * pageSize
            val games = gameDao.getGamesByPlatform(platform).drop(offset).take(pageSize)
            Result.Success(games.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Failed to fetch games by platform: ${e.message}")
        }
    }
    
    override suspend fun searchGamesLocally(query: String): Result<List<Game>> {
        return try {
            val games = gameDao.searchGamesByTitle(query)
            Result.Success(games.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error("Search failed: ${e.message}")
        }
    }
    
    override suspend fun saveGames(games: List<Game>) {
        val entities = games.map { GameEntity.fromDomain(it) }
        gameDao.insertGames(entities)
    }
    
    override suspend fun saveGame(game: Game) {
        gameDao.insertGame(GameEntity.fromDomain(game))
    }
    
    override suspend fun saveGameDetail(gameId: Long, detail: GameDetail) {
        val detailEntity = GameDetailEntity(
            gameId = gameId,
            screenshots = detail.screenshots.joinToString(","),
            videos = detail.videos.joinToString(","),
            os = detail.systemRequirements?.os,
            processor = detail.systemRequirements?.processor,
            memory = detail.systemRequirements?.memory,
            graphics = detail.systemRequirements?.graphics,
            storage = detail.systemRequirements?.storage,
            tags = detail.tags.joinToString(","),
            languages = detail.languages.joinToString(","),
            price = detail.price,
            websiteUrl = detail.websiteUrl
        )
        gameDao.insertGameDetail(detailEntity)
    }
    
    override suspend fun getGameDetail(gameId: Long): Result<GameDetail?> {
        return try {
            val game = gameDao.getGameById(gameId)
            val detail = gameDao.getGameDetailById(gameId)
            
            if (game != null) {
                val gameDetail = GameDetail(
                    game = game.toDomain(),
                    screenshots = if (detail?.screenshots?.isNotBlank() == true) 
                        detail.screenshots.split(",") else emptyList(),
                    videos = if (detail?.videos?.isNotBlank() == true) 
                        detail.videos.split(",") else emptyList(),
                    systemRequirements = if (detail?.os != null) {
                        com.example.core.model.SystemRequirements(
                            os = detail.os!!,
                            processor = detail.processor!!,
                            memory = detail.memory!!,
                            graphics = detail.graphics!!,
                            storage = detail.storage!!
                        )
                    } else null,
                    tags = if (detail?.tags?.isNotBlank() == true) 
                        detail.tags.split(",") else emptyList(),
                    languages = if (detail?.languages?.isNotBlank() == true) 
                        detail.languages.split(",") else emptyList(),
                    price = detail?.price,
                    websiteUrl = detail?.websiteUrl
                )
                Result.Success(gameDetail)
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            Result.Error("Failed to fetch game detail: ${e.message}")
        }
    }
    
    override suspend fun clearCache() {
        gameDao.deleteAllGameDetails()
        gameDao.deleteAllGames()
        gameDao.clearAllSearchCache()
    }
    
    // Reactive Flow methods
    override fun observeGames(): Flow<List<Game>> {
        return gameDao.observeAllGames().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun observeGameById(gameId: Long): Flow<Game?> {
        return gameDao.observeGameById(gameId).map { it?.toDomain() }
    }
    
    override fun observeFeaturedGames(): Flow<List<Game>> {
        return gameDao.observeFeaturedGames().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}