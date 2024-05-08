package com.example.kmp.ui.main

import com.example.shared.hanlder.EffectHandler
import com.example.shared.hanlder.EventHandler
import com.example.shared.processor.IntentProcessor
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Inject


interface MainProcessor : IntentProcessor<Intention, Action>

@Inject
class MainIntentProcessor(
    actionHandler: MainEventHandler,
    effectHandler: MainEffectHandler
) : MainProcessor,
    EventHandler<Intention.Event> by actionHandler,
    EffectHandler<Intention.Effect> by effectHandler {

    override suspend fun process(intent: Intention) {
        Napier.d("intent -> $intent")
        when (intent) {
            is Intention.Event -> handleEvent(intent)
            is Intention.Effect -> handleEffect(intent)
        }
    }
}