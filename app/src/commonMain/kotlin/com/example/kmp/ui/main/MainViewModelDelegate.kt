package com.example.kmp.ui.main

import com.example.shared.di.scope.SingleToneScope
import com.example.shared.viewmodel.ViewModelDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import me.tatarka.inject.annotations.Inject

interface MainViewModelDelegate : ViewModelDelegate<Intention, Action>

@SingleToneScope
@Inject
class MainViewModelDelegateImpl :
    MainViewModelDelegate {
    override val intents: MutableSharedFlow<Intention> = MutableSharedFlow()

    override suspend fun processIntent(intent: Intention) = intents.emit(intent)
}