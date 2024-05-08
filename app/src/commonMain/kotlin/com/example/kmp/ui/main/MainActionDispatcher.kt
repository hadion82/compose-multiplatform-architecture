package com.example.kmp.ui.main

import com.example.shared.di.scope.SingleToneScope
import com.example.shared.dispatcher.ActionDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import me.tatarka.inject.annotations.Inject

interface MainActionDispatcher : ActionDispatcher<Action>

@SingleToneScope
@Inject
class MainActionDispatcherImpl : MainActionDispatcher {
    override val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    override fun actions(): Flow<Action> = actions
}