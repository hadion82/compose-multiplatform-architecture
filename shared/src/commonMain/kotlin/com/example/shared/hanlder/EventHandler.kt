package com.example.shared.hanlder

interface EventHandler<in A> {
    suspend fun handleEvent(intent: A)
}

abstract class AbstractActionHandler<in A> : EventHandler<A> {

    override suspend fun handleEvent(intent: A) = execute(intent)

    protected abstract suspend fun execute(intent: A)
}