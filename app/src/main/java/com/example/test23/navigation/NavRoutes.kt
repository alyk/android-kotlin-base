package com.example.test23.navigation

/**
 * Sealed class representing all navigation routes in the app.
 * This provides type-safe navigation throughout the application.
 */
sealed class NavRoutes(val route: String) {
    /**
     * Discover screen - the home screen showing featured, popular, and recent games.
     */
    data object Discover : NavRoutes("discover")

    /**
     * Search screen - allows users to search for games.
     */
    data object Search : NavRoutes("search")

    /**
     * Favourites screen - displays user's favourite games.
     */
    data object Favourites : NavRoutes("favourites")

    /**
     * Game detail screen - shows detailed information about a specific game.
     * @param gameId The ID of the game to display
     */
    data object GameDetail : NavRoutes("game_detail/{gameId}") {
        const val GAME_ID_ARG = "gameId"

        /**
         * Creates a route string with the provided game ID.
         */
        fun createRoute(gameId: Long): String = "game_detail/$gameId"
    }
}

/**
 * Bottom navigation items for the main navigation bar.
 */
enum class BottomNavItem(
    val route: String,
    val title: String,
    val iconName: String
) {
    DISCOVER(NavRoutes.Discover.route, "Discover", "explore"),
    SEARCH(NavRoutes.Search.route, "Search", "search"),
    FAVOURITES(NavRoutes.Favourites.route, "Favourites", "favorite")
}