package com.example.kmp

import com.example.feature.bookmark.BookmarkViewModel
import com.example.feature.main.MainViewModel

/** Creates a separate presentation graph for each navigation back stack entry. */
interface AppViewModelFactory {
    fun createMainViewModel(): MainViewModel
    fun createBookmarkViewModel(): BookmarkViewModel
}
