package com.example.core.database.repository;

/**
 * Repository interface for user-related data operations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0003H\u00a6@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\nJ\"\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00032\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\nJ\"\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u00032\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\nJ$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0018H&J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00182\u0006\u0010\u0005\u001a\u00020\u0004H&J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00182\u0006\u0010\u0005\u001a\u00020\u0004H&J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u00182\u0006\u0010\u0005\u001a\u00020\u0004H&J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00160\u00182\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H&J$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\fH\u00a6@\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020#H\u00a6@\u00a2\u0006\u0002\u0010$\u00a8\u0006%"}, d2 = {"Lcom/example/core/database/repository/UserLocalRepository;", "", "addFavourite", "Lcom/example/core/model/Result;", "", "userId", "gameId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUser", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "Lcom/example/core/model/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavouriteCount", "", "getFavourites", "", "Lcom/example/core/model/Favourite;", "getFavouritesWithGames", "Lcom/example/core/model/FavouriteWithGame;", "isFavourited", "", "observeCurrentUser", "Lkotlinx/coroutines/flow/Flow;", "observeFavouriteCount", "observeFavourites", "observeFavouritesWithGames", "observeIsFavourited", "removeFavourite", "saveUser", "user", "(Lcom/example/core/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserPreferences", "preferences", "Lcom/example/core/model/UserPreferences;", "(JLcom/example/core/model/UserPreferences;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
public abstract interface UserLocalRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCurrentUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<com.example.core.model.User>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveUser(@org.jetbrains.annotations.NotNull()
    com.example.core.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUserPreferences(long userId, @org.jetbrains.annotations.NotNull()
    com.example.core.model.UserPreferences preferences, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteUser(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addFavourite(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Long>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeFavourite(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<kotlin.Unit>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavourites(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.Favourite>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavouritesWithGames(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<? extends java.util.List<com.example.core.model.FavouriteWithGame>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavourited(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Boolean>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavouriteCount(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.model.Result<java.lang.Integer>> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.core.model.User> observeCurrentUser();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.Favourite>> observeFavourites(long userId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.model.FavouriteWithGame>> observeFavouritesWithGames(long userId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeIsFavourited(long userId, long gameId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> observeFavouriteCount(long userId);
}