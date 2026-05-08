package com.example.core.database.datasource;

/**
 * Provides dependency injection for database layer.
 * Can be replaced with a DI framework like Hilt/Koin.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\nJ\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/core/database/datasource/DatabaseModule;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "database", "Lcom/example/core/database/AppDatabase;", "gameDao", "Lcom/example/core/database/dao/GameDao;", "userDao", "Lcom/example/core/database/dao/UserDao;", "clearAndClose", "", "provideGameDao", "provideGameLocalRepository", "Lcom/example/core/database/repository/GameLocalRepository;", "provideUserDao", "provideUserLocalRepository", "Lcom/example/core/database/repository/UserLocalRepository;", "database_debug"})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.core.database.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.core.database.dao.GameDao gameDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.core.database.dao.UserDao userDao = null;
    
    public DatabaseModule(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.dao.GameDao provideGameDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.dao.UserDao provideUserDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.repository.GameLocalRepository provideGameLocalRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.core.database.repository.UserLocalRepository provideUserLocalRepository() {
        return null;
    }
    
    /**
     * Clears all caches and closes the database
     */
    public final void clearAndClose() {
    }
}