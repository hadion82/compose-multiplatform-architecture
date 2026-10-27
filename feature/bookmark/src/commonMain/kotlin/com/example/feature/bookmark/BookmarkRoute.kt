package com.example.feature.bookmark

import androidx.compose.runtime.Composable

@Composable
fun BookmarkRoute(viewModel: BookmarkViewModel, onBack: () -> Unit) {
    BookmarkScreen(ComposeBookmarkUiState(viewModel), viewModel, onBack)
}
