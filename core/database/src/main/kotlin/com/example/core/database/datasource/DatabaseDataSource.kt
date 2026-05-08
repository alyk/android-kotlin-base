package com.example.core.database.datasource

import com.example.core.database.AppDatabase
import com.example.core.database.dao.GameDao
import com.example.core.database.dao.UserDao
import com.example.core.database.repository.GameLocalRepository
import com.example.core.database.repository.GameLocalRepositoryImpl
import com.example.core.database.repository.UserLocalRepository
import com.example.core.database.repository.UserLocalRepositoryImpl
import android.content.Context

/**
 * Data source factory for creating database instances and repositories.
 * Provides singleton access to DAOs and repositories.
 */
object DatabaseDataSource {
    
    @Volatile
    private var database: AppDatabase? = null
    
    @Volatile
    private var gameLocalRepository: GameLocalRepository? = null
    
    @Volatile
    private var userLocalRepository: UserLocalRepository? = null
    
    /**
     * Gets the database instance
     */
    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: AppDatabase.getInstance(context).also { database = it }
        }
    }
    
    /**
     * Gets the Game DAO
     */
    fun getGameDao(context: Context): GameDao {
        return getDatabase(context).gameDao()
    }
    
    /**
     * Gets the User DAO
     */
    fun getUserDao(context: Context): UserDao {
        return getDatabase(context).userDao()
    }
    
    /**
     * Gets the GameLocalRepository instance
     */
    fun getGameLocalRepository(context: Context): GameLocalRepository {
        return gameLocalRepository ?: synchronized(this) {
            gameLocalRepository ?: GameLocalRepositoryImpl(getGameDao(context))
                .also { gameLocalRepository = it }
        }
    }
    
    /**
     * Gets the UserLocalRepository instance
     */
    fun getUserLocalRepository(context: Context): UserLocalRepository {
        return userLocalRepository ?: synchronized(this) {
            userLocalRepository ?: UserLocalRepositoryImpl(getUserDao(context))
                .also { userLocalRepository = it }
        }
    }
    
    /**
     * Clears all cached instances
     */
    fun clearInstances() {
        database = null
        gameLocalRepository = null
        userLocalRepository = null
        AppDatabase.clearInstance()
    }
}

/**
 * Provides dependency injection for database layer.
 * Can be replaced with a DI framework like Hilt/Koin.
 */
class DatabaseModule {
    
    private val context: Context
    private val database: AppDatabase
    private val gameDao: GameDao
    private val userDao: UserDao
    
    constructor(context: Context) {
        this.context = context.applicationContext
        this.database = DatabaseDataSource.getDatabase(this.context)
        this.gameDao = database.gameDao()
        this.userDao = database.userDao()
    }
    
    fun provideGameDao(): GameDao = gameDao
    fun provideUserDao(): UserDao = userDao
    fun provideGameLocalRepository(): GameLocalRepository = GameLocalRepositoryImpl(gameDao)
    fun provideUserLocalRepository(): UserLocalRepository = UserLocalRepositoryImpl(userDao)
    
    /**
     * Clears all caches and closes the database
     */
    fun clearAndClose() {
        DatabaseDataSource.clearInstances()
        database.close()
    }
}