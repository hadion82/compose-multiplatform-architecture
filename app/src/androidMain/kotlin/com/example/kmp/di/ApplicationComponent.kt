package com.example.kmp.di

import android.content.Context
import com.example.kmp.di.component.ApiComponent
import com.example.kmp.di.component.DatabaseComponent
import com.example.kmp.di.component.RepositoryComponent
import com.example.kmp.di.component.ViewModelComponent
import com.example.shared.di.scope.SingleToneScope
import com.example.shared.platform.context.AndroidContext
import com.example.shared.platform.context.PlatformContext
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@SingleToneScope
@Component
abstract class ApplicationComponent(
    @get:Provides val context: Context
): DatabaseComponent, RepositoryComponent, ApiComponent, ViewModelComponent {
    @Provides
    fun AndroidContext.bind(): PlatformContext = this
}

interface ApplicationComponentProvider {
    val component: ApplicationComponent
}

val Context.applicationComponent: ApplicationComponent
    get() = (applicationContext as ApplicationComponentProvider).component