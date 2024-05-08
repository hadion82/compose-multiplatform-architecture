package com.example.kmp.ui.bookmark

import androidx.compose.ui.window.ComposeUIViewController
import com.example.kmp.di.BookmarkViewControllerComponent
import com.example.kmp.di.create
import com.example.kmp.ui.bookmark.BookmarkRoute
import com.example.kmp.ui.bookmark.ComposeBookmarkUiState

fun BookmarkViewController() = ComposeUIViewController {
    val component = BookmarkViewControllerComponent.create()
    val viewModel = component.bookmarkViewModel
    BookmarkRoute(ComposeBookmarkUiState(viewModel), viewModel)
}