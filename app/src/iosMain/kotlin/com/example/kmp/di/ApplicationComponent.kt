package com.example.kmp.di

import com.example.kmp.di.component.ApiComponent
import com.example.kmp.di.component.DatabaseComponent
import com.example.kmp.di.component.RepositoryComponent
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Component

@SingleToneScope
@Component
abstract class ApplicationComponent: DatabaseComponent, RepositoryComponent, ApiComponent {

    companion object
}

expect fun ApplicationComponent.Companion.create(): ApplicationComponent
