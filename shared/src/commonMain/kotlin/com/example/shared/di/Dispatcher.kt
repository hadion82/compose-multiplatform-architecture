package com.example.shared.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tatarka.inject.annotations.Inject

sealed interface Dispatcher {
    @Inject
    class Default{
        val dispatcher: CoroutineDispatcher = Dispatchers.Default
    }
    @Inject
    class IO {
        val dispatcher: CoroutineDispatcher = Dispatchers.IO
    }
    @Inject
    class Main {
        val dispatcher: CoroutineDispatcher = Dispatchers.Main
    }
    @Inject
    class Immediate {
        val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    }
    @Inject
    class Unconfined {
        val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined
    }
}