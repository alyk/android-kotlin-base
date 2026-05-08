package com.example.core.data.datasource

import com.example.core.model.Game
import com.example.core.model.GameDetail
import com.example.core.model.Genre
import com.example.core.model.Platform
import com.example.core.model.Result
import com.example.core.model.SearchResult
import com.example.core.model.SystemRequirements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Mock data source for development and testing.
 * Provides fake game data without making network requests.
 */
class MockGameRemoteDataSource : GameDataSource {

    private val mockGames = listOf(
        Game(
            id = 1,
            title = "The Witcher 3: Wild Hunt",
            description = "A story-driven, open world RPG set in a visually stunning fantasy universe.",
            thumbnailUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=400",
            genre = Genre.RPG,
            platform = Platform.PC,
            developer = "CD Projekt Red",
            publisher = "CD Projekt",
            releaseDate = "2015-05-18",
            rating = 4.8f,
            price = 39.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 2,
            title = "Cyberpunk 2077",
            description = "An open-world, action-adventure story set in Night City.",
            thumbnailUrl = "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=400",
            genre = Genre.ACTION,
            platform = Platform.PC,
            developer = "CD Projekt Red",
            publisher = "CD Projekt",
            releaseDate = "2020-12-10",
            rating = 4.5f,
            price = 59.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 3,
            title = "Minecraft",
            description = "A sandbox game about placing blocks and going on adventures.",
            thumbnailUrl = "https://images.unsplash.com/photo-1587573089734-09cb69c0f2b4?w=400",
            genre = Genre.ADVENTURE,
            platform = Platform.CROSSPLATFORM,
            developer = "Mojang Studios",
            publisher = "Microsoft",
            releaseDate = "2011-11-18",
            rating = 4.7f,
            price = 29.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 4,
            title = "Fortnite",
            description = "Battle royale game where players fight to be the last one standing.",
            thumbnailUrl = "https://images.unsplash.com/photo-1552820728-8b83bb6b773f?w=400",
            genre = Genre.ACTION,
            platform = Platform.CROSSPLATFORM,
            developer = "Epic Games",
            publisher = "Epic Games",
            releaseDate = "2017-07-25",
            rating = 4.4f,
            price = 0.0,
            isFree = true,
            isFeatured = true
        ),
        Game(
            id = 5,
            title = "Grand Theft Auto V",
            description = "An open world action adventure game set in Los Santos.",
            thumbnailUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=400",
            genre = Genre.ACTION,
            platform = Platform.PC,
            developer = "Rockstar Games",
            publisher = "Rockstar Games",
            releaseDate = "2015-04-14",
            rating = 4.6f,
            price = 29.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 6,
            title = "The Legend of Zelda: Breath of the Wild",
            description = "An open-world adventure game set in Hyrule.",
            thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=400",
            genre = Genre.ADVENTURE,
            platform = Platform.NINTENDO,
            developer = "Nintendo",
            publisher = "Nintendo",
            releaseDate = "2017-03-03",
            rating = 4.9f,
            price = 59.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 7,
            title = "Valorant",
            description = "A tactical 5v5 character-based shooter.",
            thumbnailUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=400",
            genre = Genre.ACTION,
            platform = Platform.PC,
            developer = "Riot Games",
            publisher = "Riot Games",
            releaseDate = "2020-06-02",
            rating = 4.3f,
            price = 0.0,
            isFree = true,
            isFeatured = false
        ),
        Game(
            id = 8,
            title = "Stardew Valley",
            description = "A farming simulation RPG with relaxing gameplay.",
            thumbnailUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=400",
            genre = Genre.SIMULATION,
            platform = Platform.CROSSPLATFORM,
            developer = "ConcernedApe",
            publisher = "ConcernedApe",
            releaseDate = "2016-02-26",
            rating = 4.8f,
            price = 14.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 9,
            title = "Civilization VI",
            description = "Turn-based strategy game to build an empire.",
            thumbnailUrl = "https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=400",
            genre = Genre.STRATEGY,
            platform = Platform.PC,
            developer = "Firaxis Games",
            publisher = "2K Games",
            releaseDate = "2016-10-21",
            rating = 4.5f,
            price = 49.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 10,
            title = "Celeste",
            description = "A challenging platformer about climbing a mountain.",
            thumbnailUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=400",
            genre = Genre.PUZZLE,
            platform = Platform.CROSSPLATFORM,
            developer = "Maddy Makes Games",
            publisher = "Maddy Makes Games",
            releaseDate = "2018-01-25",
            rating = 4.6f,
            price = 19.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 11,
            title = "Hades",
            description = "A roguelike dungeon crawler from Supergiant Games.",
            thumbnailUrl = "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=400",
            genre = Genre.INDIE,
            platform = Platform.CROSSPLATFORM,
            developer = "Supergiant Games",
            publisher = "Supergiant Games",
            releaseDate = "2020-09-17",
            rating = 4.9f,
            price = 24.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 12,
            title = "Elden Ring",
            description = "An action RPG set in a sprawling, dark fantasy world.",
            thumbnailUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=400",
            genre = Genre.RPG,
            platform = Platform.PC,
            developer = "FromSoftware",
            publisher = "Bandai Namco",
            releaseDate = "2022-02-25",
            rating = 4.7f,
            price = 59.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 13,
            title = "Apex Legends",
            description = "A free-to-play battle royale hero shooter.",
            thumbnailUrl = "https://images.unsplash.com/photo-1552820728-8b83bb6b773f?w=400",
            genre = Genre.ACTION,
            platform = Platform.CROSSPLATFORM,
            developer = "Respawn Entertainment",
            publisher = "Electronic Arts",
            releaseDate = "2019-02-04",
            rating = 4.4f,
            price = 0.0,
            isFree = true,
            isFeatured = false
        ),
        Game(
            id = 14,
            title = "Hollow Knight",
            description = "A challenging and beautiful action adventure game.",
            thumbnailUrl = "https://images.unsplash.com/photo-1587573089734-09cb69c0f2b4?w=400",
            genre = Genre.ADVENTURE,
            platform = Platform.CROSSPLATFORM,
            developer = "Team Cherry",
            publisher = "Team Cherry",
            releaseDate = "2017-02-24",
            rating = 4.8f,
            price = 14.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 15,
            title = "League of Legends",
            description = "A multiplayer online battle arena game.",
            thumbnailUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=400",
            genre = Genre.STRATEGY,
            platform = Platform.PC,
            developer = "Riot Games",
            publisher = "Riot Games",
            releaseDate = "2009-10-27",
            rating = 4.2f,
            price = 0.0,
            isFree = true,
            isFeatured = false
        ),
        Game(
            id = 16,
            title = "God of War",
            description = "An action-adventure game following Kratos and his son.",
            thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=400",
            genre = Genre.ACTION,
            platform = Platform.PLAYSTATION,
            developer = "Santa Monica Studio",
            publisher = "Sony",
            releaseDate = "2018-04-20",
            rating = 4.9f,
            price = 49.99,
            isFree = false,
            isFeatured = true
        ),
        Game(
            id = 17,
            title = "FIFA 24",
            description = "The latest installment in the popular soccer series.",
            thumbnailUrl = "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=400",
            genre = Genre.SPORTS,
            platform = Platform.CROSSPLATFORM,
            developer = "EA Sports",
            publisher = "Electronic Arts",
            releaseDate = "2023-09-22",
            rating = 4.0f,
            price = 69.99,
            isFree = false,
            isFeatured = false
        ),
        Game(
            id = 18,
            title = "League of Legends: Wild Rift",
            description = "Mobile version of League of Legends.",
            thumbnailUrl = "https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=400",
            genre = Genre.STRATEGY,
            platform = Platform.MOBILE,
            developer = "Riot Games",
            publisher = "Riot Games",
            releaseDate = "2020-10-27",
            rating = 4.3f,
            price = 0.0,
            isFree = true,
            isFeatured = false
        ),
        Game(
            id = 19,
            title = "Overwatch 2",
            description = "Team-based multiplayer first-person shooter.",
            thumbnailUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=400",
            genre = Genre.ACTION,
            platform = Platform.CROSSPLATFORM,
            developer = "Blizzard Entertainment",
            publisher = "Blizzard",
            releaseDate = "2022-10-04",
            rating = 4.2f,
            price = 0.0,
            isFree = true,
            isFeatured = false
        ),
        Game(
            id = 20,
            title = "Red Dead Redemption 2",
            description = "An epic tale of honor and loyalty in the American frontier.",
            thumbnailUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=400",
            genre = Genre.ACTION,
            platform = Platform.PC,
            developer = "Rockstar Games",
            publisher = "Rockstar Games",
            releaseDate = "2019-11-05",
            rating = 4.7f,
            price = 59.99,
            isFree = false,
            isFeatured = true
        )
    )

    override suspend fun getGames(page: Int, pageSize: Int): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            delay(500) // Simulate network delay
            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, mockGames.size)
            val games = if (startIndex < mockGames.size) {
                mockGames.subList(startIndex, endIndex)
            } else {
                emptyList()
            }
            Result.Success(games)
        }
    }

    override suspend fun getFeaturedGames(): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            val featured = mockGames.filter { it.isFeatured }
            Result.Success(featured)
        }
    }

    override suspend fun getGameById(gameId: Long): Result<GameDetail> {
        return withContext(Dispatchers.IO) {
            delay(300)
            val game = mockGames.find { it.id == gameId }
            if (game != null) {
                Result.Success(
                    GameDetail(
                        game = game,
                        screenshots = listOf(
                            "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800",
                            "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=800",
                            "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=800"
                        ),
                        videos = listOf("https://www.youtube.com/watch?v=trailer1"),
                        systemRequirements = if (game.platform == Platform.PC) {
                            SystemRequirements(
                                os = "Windows 10 64-bit",
                                processor = "Intel Core i5-8400 or AMD Ryzen 5 2600",
                                memory = "8 GB RAM",
                                graphics = "NVIDIA GeForce GTX 1060 or AMD Radeon RX 580",
                                storage = "50 GB available space"
                            )
                        } else null,
                        tags = listOf(game.genre.name, game.platform.name, "Popular"),
                        languages = listOf("English", "Spanish", "French", "German", "Japanese"),
                        price = game.price,
                        websiteUrl = "https://example.com/game/${game.id}"
                    )
                )
            } else {
                Result.Error("Game not found")
            }
        }
    }

    override suspend fun searchGames(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<SearchResult> {
        return withContext(Dispatchers.IO) {
            delay(400)
            val filtered = mockGames.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.genre.name.contains(query, ignoreCase = true)
            }
            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, filtered.size)
            val games = if (startIndex < filtered.size) {
                filtered.subList(startIndex, endIndex)
            } else {
                emptyList()
            }
            Result.Success(
                SearchResult(
                    games = games,
                    totalCount = filtered.size,
                    page = page,
                    pageSize = pageSize,
                    hasMore = endIndex < filtered.size
                )
            )
        }
    }

    override suspend fun getGamesByGenre(genre: String, page: Int, pageSize: Int): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            delay(400)
            val filtered = mockGames.filter {
                it.genre.name.equals(genre, ignoreCase = true)
            }
            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, filtered.size)
            val games = if (startIndex < filtered.size) {
                filtered.subList(startIndex, endIndex)
            } else {
                emptyList()
            }
            Result.Success(games)
        }
    }

    override suspend fun getGamesByPlatform(
        platform: String,
        page: Int,
        pageSize: Int
    ): Result<List<Game>> {
        return withContext(Dispatchers.IO) {
            delay(400)
            val filtered = mockGames.filter {
                it.platform.name.equals(platform, ignoreCase = true) ||
                (platform.equals("PC", ignoreCase = true) && it.platform == Platform.PC)
            }
            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, filtered.size)
            val games = if (startIndex < filtered.size) {
                filtered.subList(startIndex, endIndex)
            } else {
                emptyList()
            }
            Result.Success(games)
        }
    }
}