package com.example.test23.di

import com.example.core.data.datasource.GameRemoteDataSource
import com.example.core.data.repository.GameRepository
import com.example.core.data.repository.GameRepositoryImpl
import com.example.core.data.repository.UserRepository
import com.example.core.data.repository.UserRepositoryImpl
import com.example.core.database.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGameRemoteDataSource(
        gameApiService: com.example.core.data.network.GameApiService
    ): GameRemoteDataSource {
        return GameRemoteDataSource(gameApiService)
    }

    @Provides
    @Singleton
    fun provideGameRepository(gameRemoteDataSource: GameRemoteDataSource): GameRepository {
        return GameRepositoryImpl(gameRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalRepository: UserLocalRepository
    ): UserRepository {
        return UserRepositoryImpl(userLocalRepository)
    }
}