package com.example.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.bookmark.navigation.BookmarkDestination
import com.example.feature.bookmark.navigation.bookmarkScreen
import com.example.feature.main.navigation.MainDestination
import com.example.feature.main.navigation.mainScreen

@Composable
fun App(viewModelFactory: AppViewModelFactory) {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = MainDestination) {
            mainScreen(
                viewModelFactory = viewModelFactory::createMainViewModel,
                onOpenBookmarks = {
                    navController.navigate(BookmarkDestination) { launchSingleTop = true }
                }
            )
            bookmarkScreen(
                viewModelFactory = viewModelFactory::createBookmarkViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
