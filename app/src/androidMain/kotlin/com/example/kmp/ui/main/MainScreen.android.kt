package com.example.kmp.ui.main

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import com.example.kmp.ui.bookmark.BookmarkActivity

@Composable
actual fun Navigation(state: State<Action.Navigation?>) {
    val stateValue = state.value ?: return
    val context = LocalContext.current
    when (stateValue) {
        is Action.Navigation.OpenBookmark -> context.startActivity(
            Intent(context, BookmarkActivity::class.java)
        )
    }
}