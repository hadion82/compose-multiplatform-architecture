package com.example.kmp.di.scope

import kotlinx.coroutines.flow.MutableSharedFlow

interface UiScope<I> {
    val intents: MutableSharedFlow<I>
}