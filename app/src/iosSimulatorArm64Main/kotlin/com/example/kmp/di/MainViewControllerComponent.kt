package com.example.kmp.di


actual fun MainViewControllerComponent.Companion.create(): MainViewControllerComponent =
    MainViewControllerComponent::class.create(applicationComponent)