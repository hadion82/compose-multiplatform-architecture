package com.example.kmp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.kmp.di.ApplicationComponent
import com.example.kmp.di.applicationComponent
import com.example.kmp.di.viewmodel.viewModels
import com.example.shared.platform.startup.androidContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject

@Component
abstract class MainActivityComponent(
    @Component val parent: ApplicationComponent
) {
    abstract val mainViewModelConstructor: () -> MainViewModel
}

@Inject
class MainActivity : ComponentActivity(), MainScreenScope {

    private val viewModelConstructor by lazy {
        MainActivityComponent::class.create(
            androidContext.applicationComponent
        ).mainViewModelConstructor
    }

    override val intents: MutableSharedFlow<Intention> =
        MutableSharedFlow()

    private val viewModel by viewModels<MainViewModel>(viewModelConstructor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainRoute(
                uiState = ComposeMainUiState(viewModel.state()),
                presenter = ComposeMainPresenter(viewModel.intents)
            )
        }

        intents.onEach(viewModel::processIntent)
            .launchIn(lifecycleScope)
    }
}