package com.example.kmp.di.component

import com.example.kmp.ui.main.MainActionDispatcher
import com.example.kmp.ui.main.MainActionDispatcherImpl
import com.example.kmp.ui.main.MainActionReducer
import com.example.kmp.ui.main.MainActionReducerImpl
import com.example.kmp.ui.main.MainIntentProcessor
import com.example.kmp.ui.main.MainProcessor
import com.example.kmp.ui.main.MainViewModelDelegate
import com.example.kmp.ui.main.MainViewModelDelegateImpl
import me.tatarka.inject.annotations.Provides

interface ViewModelComponent {

    @Provides
    fun MainViewModelDelegateImpl.bind(): MainViewModelDelegate = this

    @Provides
    fun MainActionDispatcherImpl.bind(): MainActionDispatcher = this

    @Provides
    fun MainIntentProcessor.bind(): MainProcessor = this

    @Provides
    fun MainActionReducerImpl.bind(): MainActionReducer = this
}