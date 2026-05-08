package com.example.core.data.network

import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Result
import com.example.core.model.SearchFilter
import com.example.core.model.SearchResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Network service interface for game-related API calls.
 * Defines all available API endpoints.
 */
interface GameApiService {
    
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ApiResponse<List<Game>>
    
    @GET("games/featured")
    suspend fun getFeaturedGames(): ApiResponse<List<Game>>
    
    @GET("games/{id}")
    suspend fun getGameById(@Path("id") gameId: Long): ApiResponse<GameDetail>
    
    @GET("games/search")
    suspend fun searchGames(
        @Query("query") query: String,
        @Query("genres") genres: String? = null,
        @Query("platforms") platforms: String? = null,
        @Query("minRating") minRating: Float? = null,
        @Query("releaseYear") releaseYear: Int? = null,
        @Query("sortBy") sortBy: String = "RELEVANCE",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ApiResponse<SearchResult>
    
    @GET("games/genre/{genre}")
    suspend fun getGamesByGenre(
        @Path("genre") genre: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ApiResponse<List<Game>>
    
    @GET("games/platform/{platform}")
    suspend fun getGamesByPlatform(
        @Path("platform") platform: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ApiResponse<List<Game>>
}

/**
 * Generic API response wrapper
 */
@kotlinx.serialization.Serializable
data class ApiResponse<T>(
    val data: T,
    val success: Boolean = true,
    val message: String? = null
)

/**
 * Network client configuration and factory.
 * Provides configured Retrofit and OkHttp instances.
 */
object NetworkClient {
    
    private const val BASE_URL = "https://api.example.com/v1/"
    private const val TIMEOUT_SECONDS = 30L
    
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
    
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    
    val apiService: GameApiService by lazy {
        retrofit.create(GameApiService::class.java)
    }
    
    /**
     * Creates a custom network client with custom base URL.
     * Useful for testing or different environments.
     */
    fun createCustom(baseUrl: String): GameApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GameApiService::class.java)
    }
}