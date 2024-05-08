package com.example.shared.statemachine

interface StateMachine<S> {
    fun setState(state: S)
}