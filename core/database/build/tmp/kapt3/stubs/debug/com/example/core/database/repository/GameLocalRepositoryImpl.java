package com.example.core.database.repository;

/**
 * Implementation of GameLocalRepository using Room database.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH\u0096@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\t2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\t2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ*\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u0016J2\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u001aJ2\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u001eH\u0016J\u0018\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u001e2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u001eH\u0016J\u0016\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010&J\u001c\u0010\'\u001a\u00020\u00062\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0096@\u00a2\u0006\u0002\u0010)J\"\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010+\u001a\u00020\u0019H\u0096@\u00a2\u0006\u0002\u0010,R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/example/core/database/repository/GameLocalRepositoryImpl;", "Lcom/example/core/database/repository/GameLocalRepository;", "gameDao", "Lcom/example/core/database/dao/GameDao;", "(Lcom/example/core/database/dao/GameDao;)V", "clearCache", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFeaturedGames", "Lcom/example/core/model/Result;", "", "Lcom/example/core/model/Game;", "getGameById", "gameId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGameDetail", "Lcom/example/core/model/GameDetail;", "getGames", "page", "", "pageSize", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByGenre", "genre", "", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByPlatform", "platform", "observeFeaturedGames", "Lkotlinx/coroutines/flow/Flow;", "observeGameById", "observeGames", "saveGame", "game", "(Lcom/example/core/model/Game;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGameDetail", "detail", "(JLcom/example/core/model/GameDetail;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGames", "games", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchGamesLocally", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
public final class GameLocalRepositoryImpl implements com.example.core.database.repository.GameLocalRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.core.database.dao.GameDao gameDao = null;
    
    public GameLocalRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.core.database.dao.GameDao gameDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGames(int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFeaturedGames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGameById(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.Game>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGamesByGenre(@org.jetbrains.annotations.NotNull()
    java.lang.String genre, int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGamesByPlatform(@org.jetbrains.annotations.NotNull()
    java.lang.String platform, int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object searchGamesLocally(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveGames(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.core.model.Game> games, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveGame(@org.jetbrains.annotations.NotNull()
    com.example.core.model.Game game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveGameDetail(long gameId, @org.jetbrains.annotations.NotNull()
    com.example.core.model.GameDetail detail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGameDetail(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.GameDetail>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object clearCache(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Game>> observeGames() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.example.core.model.Game> observeGameById(long gameId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Game>> observeFeaturedGames() {
        return null;
    }
}