package com.example.feature.main

import com.example.shared.di.Dispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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

        actions().onEach { action ->
            reduce(
                if (action is Action.LoadPagingData) {
                    action.copy(data = action.data.cachedIn(viewModelScope))
                } else action
            )
        }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            process(Intention.Effect.LoadCharacters)
        }
    }
}
