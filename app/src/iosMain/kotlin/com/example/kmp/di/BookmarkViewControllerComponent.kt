package com.example.kmp.di

import com.example.feature.bookmark.BookmarkViewModel
import com.example.shared.di.scope.ViewModelScope
import me.tatarka.inject.annotations.Component

@ViewModelScope
@Component
abstract class BookmarkViewControllerComponent(
    @Component val applicationComponent: ApplicationComponent
) {
    abstract val bookmarkViewModel: BookmarkViewModel

    companion object
}

expect fun BookmarkViewControllerComponent.Companion.create(
    parent: ApplicationComponent
): BookmarkViewControllerComponent
