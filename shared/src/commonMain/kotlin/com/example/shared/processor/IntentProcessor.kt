package com.example.shared.processor

interface IntentProcessor<in I, A> {

    suspend fun process(intent: I)
}