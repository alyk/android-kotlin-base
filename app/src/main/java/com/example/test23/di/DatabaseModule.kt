package com.example.test23.di

import android.content.Context
import com.example.core.database.AppDatabase
import com.example.core.database.dao.GameDao
import com.example.core.database.dao.UserDao
import com.example.core.database.repository.GameLocalRepository
import com.example.core.database.repository.GameLocalRepositoryImpl
import com.example.core.database.repository.UserLocalRepository
import com.example.core.database.repository.UserLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideGameDao(database: AppDatabase): GameDao {
        return database.gameDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideGameLocalRepository(gameDao: GameDao): GameLocalRepository {
        return GameLocalRepositoryImpl(gameDao)
    }

    @Provides
    @Singleton
    fun provideUserLocalRepository(userDao: UserDao): UserLocalRepository {
        return UserLocalRepositoryImpl(userDao)
    }
}