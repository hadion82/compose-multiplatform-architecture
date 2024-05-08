package com.example.shared.dispatcher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface ActionDispatcher<A> {

    val actions: MutableSharedFlow<A>

    suspend fun dispatch(action: A) = actions.emit(action)

    fun actions(): Flow<A> = actions
}