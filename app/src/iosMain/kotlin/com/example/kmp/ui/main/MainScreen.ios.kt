package com.example.kmp.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.interop.LocalUIViewController
import com.example.kmp.ui.bookmark.BookmarkViewController
import platform.UIKit.navigationController

@Composable
actual fun Navigation(state: State<Action.Navigation?>) {
    val stateValue = state.value ?: return
    when (stateValue) {
        is Action.Navigation.OpenBookmark -> {
            LocalUIViewController.current.navigationController?.pushViewController(
                BookmarkViewController(), true
            )
        }
    }
}