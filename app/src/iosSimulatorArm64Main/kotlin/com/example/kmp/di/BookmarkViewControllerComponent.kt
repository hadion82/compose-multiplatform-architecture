package com.example.kmp.di

actual fun BookmarkViewControllerComponent.Companion.create(
    parent: ApplicationComponent
): BookmarkViewControllerComponent = BookmarkViewControllerComponent::class.create(parent)
