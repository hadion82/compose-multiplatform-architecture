package com.example.shared.dispatcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface IntentDispatcher<I> {
    fun dispatch(intent: I): Job
}

abstract class ScopedDispatcher<I>(
    private val intents: MutableSharedFlow<I>,
    private val scope: CoroutineScope
) : IntentDispatcher<I> {

    override fun dispatch(intent: I) =
        scope.launch { intents.emit(intent) }
}