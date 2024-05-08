package com.example.shared.hanlder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

interface EffectHandler<in E> {
    suspend fun handleEffect(intent: E)
}

abstract class AbstractEffectHandler<in E> : EffectHandler<E> {

    override suspend fun handleEffect(intent: E) {
        CoroutineScope(currentCoroutineContext())
            .launch { execute(intent) }
    }

    protected abstract suspend fun execute(intent: E)
}