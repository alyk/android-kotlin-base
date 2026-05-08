package com.example.test23.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.feature.detail.DetailScreen
import com.example.feature.detail.DetailViewModel
import com.example.feature.discover.DiscoverScreen
import com.example.feature.discover.DiscoverViewModel
import com.example.feature.favourites.FavouritesScreen
import com.example.feature.favourites.FavouritesViewModel
import com.example.feature.search.SearchScreen
import com.example.feature.search.SearchViewModel

/**
 * Main app navigation host containing the navigation graph.
 * Implements bottom navigation for main feature screens and
 * handles navigation between all feature modules.
 */
@Composable
fun Test23App(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine if bottom bar should be shown
    val showBottomBar = currentDestination?.route in listOf(
        NavRoutes.Discover.route,
        NavRoutes.Search.route,
        NavRoutes.Favourites.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Test23BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentDestination?.route
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Discover.route,
            modifier = Modifier.padding(paddingValues),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }
        ) {
            // Discover Screen
            composable(
                route = NavRoutes.Discover.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "test23://discover" },
                    navDeepLink { uriPattern = "https://test23.example.com/discover" }
                )
            ) {
                val viewModel: DiscoverViewModel = hiltViewModel()
                DiscoverScreen(
                    viewModel = viewModel,
                    onGameClick = { gameId ->
                        navigateToGameDetail(navController, gameId)
                    }
                )
            }

            // Search Screen
            composable(
                route = NavRoutes.Search.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "test23://search" },
                    navDeepLink { uriPattern = "https://test23.example.com/search" }
                )
            ) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    viewModel = viewModel,
                    onGameClick = { gameId ->
                        navigateToGameDetail(navController, gameId)
                    }
                )
            }

            // Favourites Screen
            composable(
                route = NavRoutes.Favourites.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "test23://favourites" },
                    navDeepLink { uriPattern = "https://test23.example.com/favourites" }
                )
            ) {
                val viewModel: FavouritesViewModel = hiltViewModel()
                FavouritesScreen(
                    viewModel = viewModel,
                    onGameClick = { gameId ->
                        navigateToGameDetail(navController, gameId)
                    }
                )
            }

            // Game Detail Screen
            composable(
                route = NavRoutes.GameDetail.route,
                arguments = listOf(
                    navArgument(NavRoutes.GameDetail.GAME_ID_ARG) {
                        type = NavType.LongType
                    }
                ),
                deepLinks = listOf(
                    navDeepLink { uriPattern = "test23://game/{gameId}" },
                    navDeepLink { uriPattern = "https://test23.example.com/game/{gameId}" }
                )
            ) { backStackEntry ->
                val gameId = backStackEntry.arguments?.getLong(NavRoutes.GameDetail.GAME_ID_ARG) ?: 0L
                val viewModel: DetailViewModel = hiltViewModel()
                DetailScreen(
                    viewModel = viewModel,
                    gameId = gameId,
                    onNavigateBack = { navController.popBackStack() },
                    onShowScreenshot = { /* Handle screenshot preview */ },
                    onOpenUrl = { url -> /* Handle opening URL in browser */ }
                )
            }
        }
    }
}

/**
 * Bottom navigation bar for main feature screens.
 */
@Composable
private fun Test23BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {
        BottomNavItemData.items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

/**
 * Data class for bottom navigation items.
 */
private data class BottomNavItemData(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    companion object {
        val items = listOf(
            BottomNavItemData(
                route = NavRoutes.Discover.route,
                title = "Discover",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavItemData(
                route = NavRoutes.Search.route,
                title = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            BottomNavItemData(
                route = NavRoutes.Favourites.route,
                title = "Favourites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder
            )
        )
    }
}

/**
 * Navigates to the game detail screen.
 * Properly handles the back stack by clearing intermediate destinations.
 */
private fun navigateToGameDetail(navController: NavHostController, gameId: Long) {
    navController.navigate(NavRoutes.GameDetail.createRoute(gameId))
}