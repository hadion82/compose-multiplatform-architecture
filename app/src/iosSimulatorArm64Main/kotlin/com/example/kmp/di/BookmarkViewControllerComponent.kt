package com.example.kmp.di

actual fun BookmarkViewControllerComponent.Companion.create(): BookmarkViewControllerComponent =
    BookmarkViewControllerComponent::class.create(applicationComponent)