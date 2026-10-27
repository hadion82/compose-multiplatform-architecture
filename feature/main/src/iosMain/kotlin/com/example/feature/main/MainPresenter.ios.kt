package com.example.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.shared.dispatcher.IntentDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow

class IosMainPresenter(
    dispatcher: MainScopedDispatcher
) : MultiPlatformMainPresenterDelegate, IntentDispatcher<Intention> by dispatcher {

    override fun onThumbnailClick(url: String?) {
        dispatch(Intention.Effect.SaveThumbnail(url))
    }
}

@Composable
actual fun ComposeMainPresenter(
    intents: MutableSharedFlow<Intention>,
    onOpenBookmarks: () -> Unit
): MainPresenter {
    val composeScope = rememberCoroutineScope()
    val dispatcher = MainScopedDispatcher(intents, composeScope)
    return remember(intents, composeScope, onOpenBookmarks) {
        val delegate = IosMainPresenter(dispatcher)
        MainPresenterImpl(dispatcher, delegate, onOpenBookmarks)
    }
}
