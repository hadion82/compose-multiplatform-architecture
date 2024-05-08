package com.example.kmp.ui.main

import com.example.shared.di.Dispatcher
import com.example.shared.platform.viewmodel.ViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class MainViewModel(
    processDispatcher: Dispatcher.Default,
    mainViewModelDelegate: MainViewModelDelegate,
    processor: MainProcessor,
    dispatcher: MainActionDispatcher,
    reducer: MainActionReducer,
) : ViewModel(),
    MainViewModelDelegate by mainViewModelDelegate,
    MainProcessor by processor,
    MainActionDispatcher by dispatcher,
    MainActionReducer by reducer {

    init {
        intents.onEach(::process)
            .flowOn(processDispatcher.dispatcher)
            .launchIn(viewModelScope)

        actions().onEach(::reduce)
            .launchIn(viewModelScope)

        viewModelScope.launch {
            process(Intention.Effect.LoadCharacters)
        }
    }
}