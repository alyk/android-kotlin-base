package com.example.core.database.repository;

/**
 * Implementation of UserLocalRepository using Room database.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\rJ\"\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\rJ\"\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00140\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\rJ$\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00062\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u001bH\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u001b2\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u001b2\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00140\u001b2\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00190\u001b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0016J$\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&H\u0096@\u00a2\u0006\u0002\u0010\'R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/example/core/database/repository/UserLocalRepositoryImpl;", "Lcom/example/core/database/repository/UserLocalRepository;", "userDao", "Lcom/example/core/database/dao/UserDao;", "(Lcom/example/core/database/dao/UserDao;)V", "addFavourite", "Lcom/example/core/model/Result;", "", "userId", "gameId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUser", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "Lcom/example/core/model/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavouriteCount", "", "getFavourites", "", "Lcom/example/core/model/Favourite;", "getFavouritesWithGames", "Lcom/example/core/model/FavouriteWithGame;", "isFavourited", "", "observeCurrentUser", "Lkotlinx/coroutines/flow/Flow;", "observeFavouriteCount", "observeFavourites", "observeFavouritesWithGames", "observeIsFavourited", "removeFavourite", "saveUser", "user", "(Lcom/example/core/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserPreferences", "preferences", "Lcom/example/core/model/UserPreferences;", "(JLcom/example/core/model/UserPreferences;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
public final class UserLocalRepositoryImpl implements com.example.core.database.repository.UserLocalRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.core.database.dao.UserDao userDao = null;
    
    public UserLocalRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.core.database.dao.UserDao userDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCurrentUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.User>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveUser(@org.jetbrains.annotations.NotNull()
    com.example.core.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateUserPreferences(long userId, @org.jetbrains.annotations.NotNull()
    com.example.core.model.UserPreferences preferences, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteUser(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addFavourite(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Long>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object removeFavourite(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFavourites(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Favourite>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFavouritesWithGames(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.FavouriteWithGame>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object isFavourited(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Boolean>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFavouriteCount(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Integer>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.example.core.model.User> observeCurrentUser() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Favourite>> observeFavourites(long userId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.FavouriteWithGame>> observeFavouritesWithGames(long userId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeIsFavourited(long userId, long gameId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Integer> observeFavouriteCount(long userId) {
        return null;
    }
}