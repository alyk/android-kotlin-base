package com.example.test23.navigation

/**
 * Defines all navigation routes in the app.
 */
sealed class Screen(val route: String) {
    data object Discover : Screen("discover")
    data object Search : Screen("search")
    data object Favourites : Screen("favourites")
    data object Detail : Screen("detail/{gameId}") {
        fun createRoute(gameId: Long) = "detail/$gameId"
    }
}

/**
 * Bottom navigation items
 */
enum class BottomNavItem(
    val screen: Screen,
    val title: String,
    val iconName: String
) {
    DISCOVER(Screen.Discover, "Discover", "explore"),
    SEARCH(Screen.Search, "Search", "search"),
    FAVOURITES(Screen.Favourites, "Favourites", "favorite")
}