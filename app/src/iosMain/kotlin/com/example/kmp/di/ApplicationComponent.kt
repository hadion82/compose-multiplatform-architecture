package com.example.kmp.di

import com.example.kmp.di.component.ApiComponent
import com.example.kmp.di.component.DatabaseComponent
import com.example.kmp.di.component.RepositoryComponent
import com.example.kmp.di.component.ViewModelComponent
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Component

@SingleToneScope
@Component
abstract class ApplicationComponent: DatabaseComponent, RepositoryComponent, ApiComponent,
    ViewModelComponent {

    companion object
}

expect fun ApplicationComponent.Companion.create(): ApplicationComponent

lateinit var applicationComponent: ApplicationComponent