package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.model.Favourite
import com.example.core.model.User
import com.example.core.model.UserPreferences
import com.example.core.model.Genre
import com.example.core.model.Platform

/**
 * Room entity representing a user in the local database.
 * Maps to the 'users' table.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Long,
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val createdAt: String,
    val favouriteGenres: String, // JSON serialized list
    val favouritePlatforms: String, // JSON serialized list
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
) {
    /**
     * Converts entity to domain model
     */
    fun toDomain(): User {
        return User(
            id = id,
            username = username,
            email = email,
            avatarUrl = avatarUrl,
            createdAt = createdAt,
            preferences = UserPreferences(
                favouriteGenres = parseGenreList(favouriteGenres),
                favouritePlatforms = parsePlatformList(favouritePlatforms),
                notificationsEnabled = notificationsEnabled,
                darkModeEnabled = darkModeEnabled
            )
        )
    }
    
    companion object {
        /**
         * Creates entity from domain model
         */
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                username = user.username,
                email = user.email,
                avatarUrl = user.avatarUrl,
                createdAt = user.createdAt,
                favouriteGenres = serializeGenreList(user.preferences.favouriteGenres),
                favouritePlatforms = serializePlatformList(user.preferences.favouritePlatforms),
                notificationsEnabled = user.preferences.notificationsEnabled,
                darkModeEnabled = user.preferences.darkModeEnabled
            )
        }
        
        private fun serializeGenreList(genres: List<Genre>): String {
            return genres.joinToString(",") { it.name }
        }
        
        private fun parseGenreList(data: String): List<Genre> {
            if (data.isBlank()) return emptyList()
            return data.split(",").mapNotNull { name ->
                try {
                    Genre.valueOf(name.trim())
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
        
        private fun serializePlatformList(platforms: List<Platform>): String {
            return platforms.joinToString(",") { it.name }
        }
        
        private fun parsePlatformList(data: String): List<Platform> {
            if (data.isBlank()) return emptyList()
            return data.split(",").mapNotNull { name ->
                try {
                    Platform.valueOf(name.trim())
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}

/**
 * Room entity representing a favourite game association.
 * Maps to the 'favourites' table with foreign key to users and games.
 */
@Entity(
    tableName = "favourites",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["gameId"]),
        Index(value = ["userId", "gameId"], unique = true)
    ]
)
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val gameId: Long,
    val addedAt: String
) {
    /**
     * Converts entity to domain model
     */
    fun toDomain(): Favourite {
        return Favourite(
            id = id,
            userId = userId,
            gameId = gameId,
            addedAt = addedAt
        )
    }
    
    companion object {
        /**
         * Creates entity from domain model
         */
        fun fromDomain(favourite: Favourite): FavouriteEntity {
            return FavouriteEntity(
                id = favourite.id,
                userId = favourite.userId,
                gameId = favourite.gameId,
                addedAt = favourite.addedAt
            )
        }
    }
}

/**
 * Composite entity for favourite with game details.
 * Used for querying favourites with full game information.
 */
data class FavouriteWithGameEntity(
    val favouriteId: Long,
    val userId: Long,
    val gameId: Long,
    val addedAt: String,
    val gameTitle: String,
    val gameDescription: String,
    val gameThumbnailUrl: String,
    val gameGenre: String,
    val gamePlatform: String,
    val gameReleaseDate: String,
    val gameRating: Float,
    val gameDeveloper: String,
    val gamePublisher: String,
    val gameIsFeatured: Boolean
)