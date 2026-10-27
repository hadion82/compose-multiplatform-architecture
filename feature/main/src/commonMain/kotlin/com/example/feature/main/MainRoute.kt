package com.example.feature.main

import androidx.compose.runtime.Composable

@Composable
fun MainRoute(viewModel: MainViewModel, onOpenBookmarks: () -> Unit) {
    MainScreen(
        uiState = ComposeMainUiState(viewModel.state()),
        presenter = ComposeMainPresenter(viewModel.intents, onOpenBookmarks)
    )
}
