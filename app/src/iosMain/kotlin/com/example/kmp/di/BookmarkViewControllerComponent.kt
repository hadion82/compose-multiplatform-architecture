package com.example.kmp.di

import com.example.kmp.ui.bookmark.BookmarkViewModel
import com.example.kmp.ui.main.MainViewModel
import me.tatarka.inject.annotations.Component

@Component
abstract class BookmarkViewControllerComponent(
    @Component val applicationComponent: ApplicationComponent
) {
    abstract val bookmarkViewModel : BookmarkViewModel

    companion object
}

expect fun BookmarkViewControllerComponent.Companion.create(): BookmarkViewControllerComponent