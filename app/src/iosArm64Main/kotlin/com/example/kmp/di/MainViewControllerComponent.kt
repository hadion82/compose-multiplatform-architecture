package com.example.kmp.di


actual fun MainViewControllerComponent.Companion.create(
    parent: ApplicationComponent
): MainViewControllerComponent = MainViewControllerComponent::class.create(parent)
