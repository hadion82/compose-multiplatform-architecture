package com.example.feature.bookmark.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.bookmark.BookmarkRoute
import com.example.feature.bookmark.BookmarkViewModel
import kotlinx.serialization.Serializable

@Serializable
data object BookmarkDestination

fun NavGraphBuilder.bookmarkScreen(
    viewModelFactory: () -> BookmarkViewModel,
    onBack: () -> Unit
) {
    composable<BookmarkDestination> {
        val viewModel = viewModel { viewModelFactory() }
        BookmarkRoute(viewModel, onBack)
    }
}
