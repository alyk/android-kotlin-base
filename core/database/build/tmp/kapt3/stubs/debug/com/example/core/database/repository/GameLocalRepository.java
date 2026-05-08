package com.example.core.database.repository;

/**
 * Repository interface for game data operations.
 * Abstracts the data source from the rest of the application.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00062\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u00062\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\fJ.\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u0013J6\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u0017J6\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0019\u001a\u00020\u00162\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001bH&J\u0018\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u001b2\u0006\u0010\n\u001a\u00020\u000bH&J\u0014\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001bH&J\u0016\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010#J\u001c\u0010$\u001a\u00020\u00032\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00a6@\u00a2\u0006\u0002\u0010&J\"\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010(\u001a\u00020\u0016H\u00a6@\u00a2\u0006\u0002\u0010)\u00a8\u0006*"}, d2 = {"Lcom/example/core/database/repository/GameLocalRepository;", "", "clearCache", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFeaturedGames", "Lcom/example/core/model/Result;", "", "Lcom/example/core/model/Game;", "getGameById", "gameId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGameDetail", "Lcom/example/core/model/GameDetail;", "getGames", "page", "", "pageSize", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByGenre", "genre", "", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGamesByPlatform", "platform", "observeFeaturedGames", "Lkotlinx/coroutines/flow/Flow;", "observeGameById", "observeGames", "saveGame", "game", "(Lcom/example/core/model/Game;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGameDetail", "detail", "(JLcom/example/core/model/GameDetail;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGames", "games", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchGamesLocally", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
public abstract interface GameLocalRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGames(int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFeaturedGames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGameById(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.Game>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesByGenre(@org.jetbrains.annotations.NotNull()
    java.lang.String genre, int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesByPlatform(@org.jetbrains.annotations.NotNull()
    java.lang.String platform, int page, int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchGamesLocally(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Game>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveGames(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.core.model.Game> games, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveGame(@org.jetbrains.annotations.NotNull()
    com.example.core.model.Game game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveGameDetail(long gameId, @org.jetbrains.annotations.NotNull()
    com.example.core.model.GameDetail detail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGameDetail(long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.GameDetail>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearCache(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Game>> observeGames();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.core.model.Game> observeGameById(long gameId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Game>> observeFeaturedGames();
    
    /**
     * Repository interface for game data operations.
     * Abstracts the data source from the rest of the application.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}