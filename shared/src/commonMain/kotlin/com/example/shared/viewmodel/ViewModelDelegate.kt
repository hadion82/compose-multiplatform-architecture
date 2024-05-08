package com.example.shared.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow

interface ViewModelDelegate<I, out S> {

    val intents: MutableSharedFlow<I>

    suspend fun processIntent(intent: I)
}