package com.example.feature.main.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.main.MainRoute
import com.example.feature.main.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
data object MainDestination

fun NavGraphBuilder.mainScreen(
    viewModelFactory: () -> MainViewModel,
    onOpenBookmarks: () -> Unit
) {
    composable<MainDestination> {
        val viewModel = viewModel { viewModelFactory() }
        MainRoute(viewModel, onOpenBookmarks)
    }
}
