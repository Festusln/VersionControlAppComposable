package com.example.versioncontrolappcomposable.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.versioncontrolappcomposable.ui.screens.HomeScreen
import com.example.versioncontrolappcomposable.ui.screens.RepoScreen
import com.example.versioncontrolappcomposable.ui.screens.RepoSearchScreen
import com.example.versioncontrolappcomposable.ui.screens.UserScreen
import com.example.versioncontrolappcomposable.ui.screens.UserSearchScreen

@Composable
fun AppNavigationGraph()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController)
        }

        composable(Routes.REPO_SCREEN) {
            RepoScreen(navController)
        }

        composable(Routes.USER_SCREEN) {
            UserScreen(navController)
        }

        composable("${Routes.SEARCH_USER_SCREEN}/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""

            UserSearchScreen(navController, username)
        }

        composable("${Routes.SEARCH_REPO_SCREEN}/{repoName}") { backStackEntry ->
            val repoName = backStackEntry.arguments?.getString("repoName") ?: ""

            RepoSearchScreen(navController, repoName)
        }

    }
}
