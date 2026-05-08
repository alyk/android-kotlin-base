package com.example.core.database.datasource;

/**
 * Data source factory for creating database instances and repositories.
 * Provides singleton access to DAOs and repositories.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/core/database/datasource/DatabaseDataSource;", "", "()V", "database", "Lcom/example/core/database/AppDatabase;", "gameLocalRepository", "Lcom/example/core/database/repository/GameLocalRepository;", "userLocalRepository", "Lcom/example/core/database/repository/UserLocalRepository;", "clearInstances", "", "getDatabase", "context", "Landroid/content/Context;", "getGameDao", "Lcom/example/core/database/dao/GameDao;", "getGameLocalRepository", "getUserDao", "Lcom/example/core/database/dao/UserDao;", "getUserLocalRepository", "database_debug"})
public final class DatabaseDataSource {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.core.database.AppDatabase database;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.core.database.repository.GameLocalRepository gameLocalRepository;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.core.database.repository.UserLocalRepository userLocalRepository;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.core.database.datasource.DatabaseDataSource INSTANCE = null;
    
    private DatabaseDataSource() {
        super();
    }
    
    /**
     * Gets the database instance
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Gets the Game DAO
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.dao.GameDao getGameDao(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Gets the User DAO
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.dao.UserDao getUserDao(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Gets the GameLocalRepository instance
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.repository.GameLocalRepository getGameLocalRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Gets the UserLocalRepository instance
     */
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.repository.UserLocalRepository getUserLocalRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Clears all cached instances
     */
    public final void clearInstances() {
    }
}