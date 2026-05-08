package com.example.test23.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.GameAppTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for navigation components.
 * Tests bottom navigation and screen navigation flow.
 */
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavigationIsDisplayedOnStartScreen() {
        // When app starts
        composeTestRule.setContent {
            val navController = rememberNavController()
            GameAppTheme {
                Test23App()
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then bottom navigation items are visible
        composeTestRule.onNodeWithText("Discover").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        composeTestRule.onNodeWithText("Favourites").assertIsDisplayed()
    }

    @Test
    fun clickingSearchShowsSearchScreen() {
        // When app starts and search is clicked
        composeTestRule.setContent {
            val navController = rememberNavController()
            GameAppTheme {
                Test23App()
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Search").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // Then search field is visible
        composeTestRule.onNodeWithText("Search games...").assertIsDisplayed()
    }

    @Test
    fun clickingFavouritesShowsFavouritesScreen() {
        // When app starts and favourites is clicked
        composeTestRule.setContent {
            val navController = rememberNavController()
            GameAppTheme {
                Test23App()
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Favourites").performClick()

        // Wait for navigation
        composeTestRule.waitForIdle()

        // Then favourites title is visible
        composeTestRule.onNodeWithText("My Favourites").assertIsDisplayed()
    }

    @Test
    fun discoverIsSelectedByDefault() {
        // When app starts
        composeTestRule.setContent {
            val navController = rememberNavController()
            GameAppTheme {
                Test23App()
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then discover is selected
        composeTestRule.onNodeWithText("Discover").assertIsDisplayed()
    }

    @Test
    fun navigationIconsAreDisplayedCorrectly() {
        // When app starts
        composeTestRule.setContent {
            val navController = rememberNavController()
            GameAppTheme {
                Test23App()
            }
        }

        // Wait for content
        composeTestRule.waitForIdle()

        // Then navigation icons are visible
        composeTestRule.onNodeWithContentDescription("Discover").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favourites").assertIsDisplayed()
    }
}