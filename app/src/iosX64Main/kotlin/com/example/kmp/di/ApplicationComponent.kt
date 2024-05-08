package com.example.kmp.di

actual fun ApplicationComponent.Companion.create() =
    ApplicationComponent::class.create().also { component ->
        applicationComponent = component
    }