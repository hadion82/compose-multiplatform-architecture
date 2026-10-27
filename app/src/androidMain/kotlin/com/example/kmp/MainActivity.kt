package com.example.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feature.bookmark.BookmarkViewModel
import com.example.feature.main.MainViewModel
import com.example.feature.main.di.MainViewModelBindings
import com.example.kmp.di.ApplicationComponent
import com.example.kmp.di.applicationComponent
import com.example.shared.di.scope.ViewModelScope
import me.tatarka.inject.annotations.Component

@ViewModelScope
@Component
abstract class MainScreenComponent(
    @Component val parent: ApplicationComponent
) : MainViewModelBindings {
    abstract val viewModel: MainViewModel
}

@ViewModelScope
@Component
abstract class BookmarkScreenComponent(
    @Component val parent: ApplicationComponent
) {
    abstract val viewModel: BookmarkViewModel
}

private class AndroidAppViewModelFactory(
    private val component: ApplicationComponent
) : AppViewModelFactory {
    override fun createMainViewModel(): MainViewModel =
        MainScreenComponent::class.create(component).viewModel

    override fun createBookmarkViewModel(): BookmarkViewModel =
        BookmarkScreenComponent::class.create(component).viewModel
}

class MainActivity : ComponentActivity() {
    private val viewModelFactory: AppViewModelFactory by lazy {
        AndroidAppViewModelFactory(applicationComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App(viewModelFactory) }
    }
}
