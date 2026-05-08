package com.example.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.database.dao.GameDao
import com.example.core.database.dao.UserDao
import com.example.core.database.entity.FavouriteEntity
import com.example.core.database.entity.GameDetailEntity
import com.example.core.database.entity.GameEntity
import com.example.core.database.entity.SearchCacheEntity
import com.example.core.database.entity.UserEntity

/**
 * Room database for the application.
 * Manages all database entities and provides DAOs.
 */
@Database(
    entities = [
        GameEntity::class,
        GameDetailEntity::class,
        SearchCacheEntity::class,
        UserEntity::class,
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun gameDao(): GameDao
    abstract fun userDao(): UserDao
    
    companion object {
        private const val DATABASE_NAME = "game_database"
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Gets the singleton database instance.
         * Thread-safe with double-checked locking.
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        
        /**
         * Creates an in-memory database for testing.
         * Data is lost when the process is killed.
         */
        fun createInMemoryDatabase(context: Context): AppDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                AppDatabase::class.java
            ).build()
        }
        
        /**
         * Clears the singleton instance.
         * Useful for testing.
         */
        fun clearInstance() {
            INSTANCE = null
        }
    }
}