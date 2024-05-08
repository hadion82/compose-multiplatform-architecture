package com.example.kmp.ui.main

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ComposeUIViewController
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.example.kmp.di.MainViewControllerComponent
import com.example.kmp.di.create
import com.example.kmp.imageloader.createImageLoader
import com.example.kmp.ui.main.ComposeMainPresenter
import com.example.kmp.ui.main.ComposeMainUiState
import com.example.kmp.ui.main.Intention
import com.example.kmp.ui.main.MainRoute
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoilApi::class)
fun MainViewController() = ComposeUIViewController {
    Napier.base(DebugAntilog())
    setSingletonImageLoaderFactory { context: PlatformContext ->
        createImageLoader(context)
    }
    val component = MainViewControllerComponent.create()
    val intents: MutableSharedFlow<Intention> =
        MutableStateFlow(Intention.Effect.LoadCharacters)
    val viewModel = component.mainViewModel
    val composeScope = rememberCoroutineScope()
    MainRoute(ComposeMainUiState(viewModel.state()), ComposeMainPresenter(intents))
    intents.onEach(viewModel::processIntent)
        .launchIn(composeScope)
}