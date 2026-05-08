package com.example.test23.di

import com.example.core.data.datasource.GameDataSource
import com.example.core.data.datasource.GameRemoteDataSource
import com.example.core.data.datasource.MockGameRemoteDataSource
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

    /**
     * Flag to enable mock data for development.
     * Set to false to use real API calls.
     */
    private const val USE_MOCK_DATA = true

    @Provides
    @Singleton
    fun provideGameDataSource(
        gameApiService: com.example.core.data.network.GameApiService
    ): GameDataSource {
        return if (USE_MOCK_DATA) {
            MockGameRemoteDataSource()
        } else {
            GameRemoteDataSource(gameApiService)
        }
    }

    @Provides
    @Singleton
    fun provideGameRepository(gameDataSource: GameDataSource): GameRepository {
        return GameRepositoryImpl(gameDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalRepository: UserLocalRepository
    ): UserRepository {
        return UserRepositoryImpl(userLocalRepository)
    }
}