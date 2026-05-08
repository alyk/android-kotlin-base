package com.example.core.database.dao;

/**
 * Data Access Object for user-related database operations.
 * Provides methods for CRUD operations on users and favourites.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u00162\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00162\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001d\u001a\u00020\u001eH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010!\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\"\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010#\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010$\u001a\u00020%2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00160\'H\'J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00180\'2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00160\'2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00160\'2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001e\u0010+\u001a\b\u0012\u0004\u0012\u00020%0\'2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\'J\u0018\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\'2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0016\u0010-\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006."}, d2 = {"Lcom/example/core/database/dao/UserDao;", "", "deleteAllFavourites", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllFavouritesForUser", "userId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllUsers", "deleteFavourite", "favourite", "Lcom/example/core/database/entity/FavouriteEntity;", "(Lcom/example/core/database/entity/FavouriteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFavouriteByIds", "gameId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUser", "user", "Lcom/example/core/database/entity/UserEntity;", "(Lcom/example/core/database/entity/UserEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUsers", "", "getFavouriteCount", "", "getFavouritesByUser", "getFavouritesWithGamesByUser", "Lcom/example/core/database/entity/FavouriteWithGameEntity;", "getUserByEmail", "email", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserById", "getUserCount", "insertFavourite", "insertUser", "isFavourited", "", "observeAllUsers", "Lkotlinx/coroutines/flow/Flow;", "observeFavouriteCount", "observeFavouritesByUser", "observeFavouritesWithGamesByUser", "observeIsFavourited", "observeUserById", "updateUser", "database_debug"})
@androidx.room.Dao()
public abstract interface UserDao {
    
    /**
     * Inserts a user into the database
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertUser(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.UserEntity user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Updates an existing user
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUser(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.UserEntity user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a user from the database
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteUser(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.UserEntity user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets a user by their ID
     */
    @androidx.room.Query(value = "SELECT * FROM users WHERE id = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserById(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.database.entity.UserEntity> $completion);
    
    /**
     * Gets a user by their ID as Flow for reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM users WHERE id = :userId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.core.database.entity.UserEntity> observeUserById(long userId);
    
    /**
     * Gets a user by their email
     */
    @androidx.room.Query(value = "SELECT * FROM users WHERE email = :email")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.core.database.entity.UserEntity> $completion);
    
    /**
     * Gets all users
     */
    @androidx.room.Query(value = "SELECT * FROM users")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.UserEntity>> $completion);
    
    /**
     * Gets all users as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM users")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.UserEntity>> observeAllUsers();
    
    /**
     * Gets the count of all users
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM users")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Deletes all users
     */
    @androidx.room.Query(value = "DELETE FROM users")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Inserts a favourite into the database
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFavourite(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.FavouriteEntity favourite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Deletes a favourite from the database
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFavourite(@org.jetbrains.annotations.NotNull()
    com.example.core.database.entity.FavouriteEntity favourite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a favourite by user ID and game ID
     */
    @androidx.room.Query(value = "DELETE FROM favourites WHERE userId = :userId AND gameId = :gameId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFavouriteByIds(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets all favourites for a user
     */
    @androidx.room.Query(value = "SELECT * FROM favourites WHERE userId = :userId ORDER BY addedAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavouritesByUser(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.FavouriteEntity>> $completion);
    
    /**
     * Gets all favourites for a user as Flow
     */
    @androidx.room.Query(value = "SELECT * FROM favourites WHERE userId = :userId ORDER BY addedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.FavouriteEntity>> observeFavouritesByUser(long userId);
    
    /**
     * Gets favourite with full game details for a user
     */
    @androidx.room.Query(value = "\n        SELECT \n            f.id as favouriteId,\n            f.userId,\n            f.gameId,\n            f.addedAt,\n            g.title as gameTitle,\n            g.description as gameDescription,\n            g.thumbnailUrl as gameThumbnailUrl,\n            g.genre as gameGenre,\n            g.platform as gamePlatform,\n            g.releaseDate as gameReleaseDate,\n            g.rating as gameRating,\n            g.developer as gameDeveloper,\n            g.publisher as gamePublisher,\n            g.isFeatured as gameIsFeatured\n        FROM favourites f\n        INNER JOIN games g ON f.gameId = g.id\n        WHERE f.userId = :userId\n        ORDER BY f.addedAt DESC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavouritesWithGamesByUser(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.core.database.entity.FavouriteWithGameEntity>> $completion);
    
    /**
     * Gets favourite with full game details as Flow
     */
    @androidx.room.Query(value = "\n        SELECT \n            f.id as favouriteId,\n            f.userId,\n            f.gameId,\n            f.addedAt,\n            g.title as gameTitle,\n            g.description as gameDescription,\n            g.thumbnailUrl as gameThumbnailUrl,\n            g.genre as gameGenre,\n            g.platform as gamePlatform,\n            g.releaseDate as gameReleaseDate,\n            g.rating as gameRating,\n            g.developer as gameDeveloper,\n            g.publisher as gamePublisher,\n            g.isFeatured as gameIsFeatured\n        FROM favourites f\n        INNER JOIN games g ON f.gameId = g.id\n        WHERE f.userId = :userId\n        ORDER BY f.addedAt DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.core.database.entity.FavouriteWithGameEntity>> observeFavouritesWithGamesByUser(long userId);
    
    /**
     * Checks if a game is favourited by a user
     */
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = :userId AND gameId = :gameId)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavourited(long userId, long gameId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    /**
     * Checks if a game is favourited as Flow
     */
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM favourites WHERE userId = :userId AND gameId = :gameId)")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeIsFavourited(long userId, long gameId);
    
    /**
     * Gets the count of favourites for a user
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM favourites WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavouriteCount(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Gets the count of favourites for a user as Flow
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM favourites WHERE userId = :userId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> observeFavouriteCount(long userId);
    
    /**
     * Deletes all favourites for a user
     */
    @androidx.room.Query(value = "DELETE FROM favourites WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllFavouritesForUser(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes all favourites
     */
    @androidx.room.Query(value = "DELETE FROM favourites")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllFavourites(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}