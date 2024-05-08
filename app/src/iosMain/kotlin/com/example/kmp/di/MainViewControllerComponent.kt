package com.example.kmp.di

import com.example.kmp.ui.main.MainViewModel
import me.tatarka.inject.annotations.Component

@Component
abstract class MainViewControllerComponent(
    @Component val applicationComponent: ApplicationComponent
) {
    abstract val mainViewModel : MainViewModel

    companion object
}

expect fun MainViewControllerComponent.Companion.create(): MainViewControllerComponent