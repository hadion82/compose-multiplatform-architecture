package com.example.kmp.ui.main

import androidx.compose.runtime.Composable

@Composable
fun MainRoute(uiState: MainComposableUiState, presenter: MainPresenter) {
    MainScreen(uiState, presenter)
    Navigation(uiState.navigation)
}