package com.example.shared.reducer

interface ActionReducer<S, in A> {
    suspend fun reduce(action: A)

    fun state(): S
}