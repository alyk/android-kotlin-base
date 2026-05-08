package com.example.core.database.dao;

/**
 * Data Access Object for game-related database operations.
 * Provides methods for CRUD operations on games and game details.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0012\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010 \u001a\u00020!H\u00a7@\u00a2\u0006\u0002\u0010\"J\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010$\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\"\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\'\u001a\u00020\u001d2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010,J\u001c\u0010-\u001a\u00020\u00032\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u00a7@\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\u00032\u0006\u00101\u001a\u00020&H\u00a7@\u00a2\u0006\u0002\u00102J\u0014\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u001404H\'J\u0014\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u001404H\'J\u0018\u00106\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f042\u0006\u0010\u0012\u001a\u00020\u0007H\'J\u0018\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a042\u0006\u0010\u0012\u001a\u00020\u0007H\'J\u001c\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u0014042\u0006\u0010\u001c\u001a\u00020\u001dH\'J\u001c\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u0014042\u0006\u0010$\u001a\u00020\u001dH\'J\u001c\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u0014042\u0006\u0010\'\u001a\u00020\u001dH\'J\u001c\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\'\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010<\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006="}, d2 = {"Lcom/example/core/database/dao/GameDao;", "", "clearAllSearchCache", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearExpiredSearchCache", "currentTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearOldGames", "timestamp", "deleteAllGameDetails", "deleteAllGames", "deleteGame", "game", "Lcom/example/core/database/entity/GameEntity;", "(Lcom/example/core/database/entity/GameEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteGameDetail", "gameId", "getAllGames", "", "getFeaturedGames", "getGameById", "getGameCount", "", "getGameDetailById", "Lcom/example/core/database/entity/GameDetailEntity;", "getGamesByGenre", "genre", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByMinRating", "minRating", "", "(FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByPlatform", "platform", "getSearchCache", "Lcom/example/core/database/entity/SearchCacheEntity;", "query", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertGame", "insertGameDetail", "detail", "(Lcom/example/core/database/entity/GameDetailEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertGames", "games", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSearchCache", "cache", "(Lcom/example/core/database/entity/SearchCacheEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllGames", "Lkotlinx/coroutines/flow/Flow;", "observeFeaturedGames", "observeGameById", "observeGameDetailById", "observeGamesByGenre", "observeGamesByPlatform", "observeSearchGamesByTitle", "searchGamesByTitle", "updateGame", "database_debug"})
@androidx.room.Dao()
public abstract interface GameDao {
    
    /**
     * Inserts a list of games into the database
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertGames(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.core.database.entity.GameEntity> games, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Inserts a single game into the database
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertGame(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.GameEntity game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Updates an existing game
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateGame(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.GameEntity game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a game from the database
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteGame(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.GameEntity game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes all games from the database
     */
    @androidx.room.Query(value = "DELETE FROM games")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllGames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets a game by its ID
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE id = :gameId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGameById(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.database.entity.GameEntity> $completion);
    
    /**
     * Gets a game by its ID as Flow for reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE id = :gameId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.core.database.entity.GameEntity> observeGameById(long gameId);
    
    /**
     * Gets all games from the database
     */
    @androidx.room.Query(value = "SELECT * FROM games ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllGames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Gets all games as Flow for reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM games ORDER BY rating DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.GameEntity>> observeAllGames();
    
    /**
     * Gets featured games
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE isFeatured = 1 ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFeaturedGames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Gets featured games as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE isFeatured = 1 ORDER BY rating DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.GameEntity>> observeFeaturedGames();
    
    /**
     * Gets games by genre
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE genre = :genre ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesByGenre(@org.jetbrains.annotations.NotNull()
    java.lang.String genre, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Gets games by genre as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE genre = :genre ORDER BY rating DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.GameEntity>> observeGamesByGenre(@org.jetbrains.annotations.NotNull()
    java.lang.String genre);
    
    /**
     * Gets games by platform
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE platform = :platform ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesByPlatform(@org.jetbrains.annotations.NotNull()
    java.lang.String platform, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Gets games by platform as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE platform = :platform ORDER BY rating DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.GameEntity>> observeGamesByPlatform(@org.jetbrains.annotations.NotNull()
    java.lang.String platform);
    
    /**
     * Searches games by title (case-insensitive)
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE title LIKE \'%\' || :query || \'%\' ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchGamesByTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Searches games by title as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE title LIKE \'%\' || :query || \'%\' ORDER BY rating DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.GameEntity>> observeSearchGamesByTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    /**
     * Gets games with rating greater than or equal to specified value
     */
    @androidx.room.Query(value = "SELECT * FROM games WHERE rating >= :minRating ORDER BY rating DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesByMinRating(float minRating, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.GameEntity>> $completion);
    
    /**
     * Gets the count of all games
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM games")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGameCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Clears games older than specified timestamp
     */
    @androidx.room.Query(value = "DELETE FROM games WHERE cachedAt < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearOldGames(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Inserts or replaces game detail
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertGameDetail(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.GameDetailEntity detail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets game detail by game ID
     */
    @androidx.room.Query(value = "SELECT * FROM game_details WHERE gameId = :gameId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGameDetailById(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.database.entity.GameDetailEntity> $completion);
    
    /**
     * Gets game detail by game ID as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM game_details WHERE gameId = :gameId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.core.database.entity.GameDetailEntity> observeGameDetailById(long gameId);
    
    /**
     * Deletes game detail by game ID
     */
    @androidx.room.Query(value = "DELETE FROM game_details WHERE gameId = :gameId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteGameDetail(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes all game details
     */
    @androidx.room.Query(value = "DELETE FROM game_details")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllGameDetails(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Saves search cache entry
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSearchCache(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.SearchCacheEntity cache, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets search cache entry by query
     */
    @androidx.room.Query(value = "SELECT * FROM search_cache WHERE query = :query AND expiresAt > :currentTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSearchCache(@org.jetbrains.annotations.NotNull()
    java.lang.String query, long currentTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.database.entity.SearchCacheEntity> $completion);
    
    /**
     * Clears expired search cache entries
     */
    @androidx.room.Query(value = "DELETE FROM search_cache WHERE expiresAt < :currentTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearExpiredSearchCache(long currentTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Clears all search cache
     */
    @androidx.room.Query(value = "DELETE FROM search_cache")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearAllSearchCache(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Data Access Object for game-related database operations.
     * Provides methods for CRUD operations on games and game details.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}