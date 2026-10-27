package com.example.feature.main.di

import com.example.feature.main.MainActionDispatcher
import com.example.feature.main.MainActionDispatcherImpl
import com.example.feature.main.MainActionReducer
import com.example.feature.main.MainActionReducerImpl
import com.example.feature.main.MainIntentProcessor
import com.example.feature.main.MainProcessor
import com.example.feature.main.MainViewModelDelegate
import com.example.feature.main.MainViewModelDelegateImpl
import me.tatarka.inject.annotations.Provides

/** Included by a screen-scoped component in the application's composition root. */
interface MainViewModelBindings {
    @Provides
    fun MainViewModelDelegateImpl.bind(): MainViewModelDelegate = this

    @Provides
    fun MainActionDispatcherImpl.bind(): MainActionDispatcher = this

    @Provides
    fun MainIntentProcessor.bind(): MainProcessor = this

    @Provides
    fun MainActionReducerImpl.bind(): MainActionReducer = this
}
