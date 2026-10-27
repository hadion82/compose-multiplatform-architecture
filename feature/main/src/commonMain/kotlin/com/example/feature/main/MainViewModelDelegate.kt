package com.example.feature.main

import com.example.shared.di.scope.ViewModelScope
import com.example.shared.viewmodel.ViewModelDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import me.tatarka.inject.annotations.Inject

interface MainViewModelDelegate : ViewModelDelegate<Intention, Action>

@ViewModelScope
@Inject
class MainViewModelDelegateImpl :
    MainViewModelDelegate {
    override val intents: MutableSharedFlow<Intention> = MutableSharedFlow()

    override suspend fun processIntent(intent: Intention) = intents.emit(intent)
}