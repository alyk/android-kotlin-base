package com.example.test23.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.feature.detail.DetailScreen
import com.example.feature.detail.DetailViewModel
import com.example.feature.favourites.FavouritesScreen
import com.example.feature.favourites.FavouritesViewModel
import com.example.feature.search.SearchScreen
import com.example.feature.search.SearchViewModel

/**
 * Main navigation graph for the app.
 * Connects all feature modules with smooth transitions and proper back stack management.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val animationDuration = 300

    NavHost(
        navController = navController,
        startDestination = Screen.Discover.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = tween(animationDuration)) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(animationDuration)
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animationDuration)) +
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(animationDuration)
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animationDuration)) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(animationDuration)
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(animationDuration)) +
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(animationDuration)
                    )
        }
    ) {
        // Discover Screen
        composable(route = Screen.Discover.route) {
            // TODO: Add DiscoverScreen when implemented
            // DiscoverScreen(
            //     onGameClick = { gameId ->
            //         navController.navigate(Screen.Detail.createRoute(gameId))
            //     }
            // )
        }

        // Search Screen
        composable(route = Screen.Search.route) {
            val viewModel: SearchViewModel = hiltViewModel()

            SearchScreen(
                viewModel = viewModel,
                onGameClick = { gameId ->
                    navController.navigate(Screen.Detail.createRoute(gameId))
                }
            )
        }

        // Favourites Screen
        composable(route = Screen.Favourites.route) {
            val viewModel: FavouritesViewModel = hiltViewModel()

            FavouritesScreen(
                viewModel = viewModel,
                onGameClick = { gameId ->
                    navController.navigate(Screen.Detail.createRoute(gameId))
                }
            )
        }

        // Detail Screen with deep linking support
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "test23://game/{gameId}" },
                navDeepLink { uriPattern = "https://test23.com/game/{gameId}" }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: 0L
            val viewModel: DetailViewModel = hiltViewModel()

            DetailScreen(
                viewModel = viewModel,
                gameId = gameId,
                onNavigateBack = { navController.popBackStack() },
                onShowScreenshot = { url ->
                    // TODO: Implement screenshot viewer
                },
                onOpenUrl = { url ->
                    // TODO: Implement URL opener
                }
            )
        }
    }
}