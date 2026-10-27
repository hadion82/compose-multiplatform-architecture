package com.example.kmp.di

import com.example.feature.main.MainViewModel
import com.example.feature.main.di.MainViewModelBindings
import com.example.shared.di.scope.ViewModelScope
import me.tatarka.inject.annotations.Component

@ViewModelScope
@Component
abstract class MainViewControllerComponent(
    @Component val applicationComponent: ApplicationComponent
) : MainViewModelBindings {
    abstract val mainViewModel: MainViewModel

    companion object
}

expect fun MainViewControllerComponent.Companion.create(
    parent: ApplicationComponent
): MainViewControllerComponent
