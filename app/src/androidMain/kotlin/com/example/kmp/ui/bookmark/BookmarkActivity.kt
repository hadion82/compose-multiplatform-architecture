package com.example.kmp.ui.bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kmp.di.ApplicationComponent
import com.example.kmp.di.applicationComponent
import com.example.kmp.di.viewmodel.viewModels
import com.example.shared.platform.startup.androidContext
import me.tatarka.inject.annotations.Component

@Component
abstract class BookmarkActivityComponent(
    @Component val parent: ApplicationComponent
) {
    abstract val bookmarkViewModelConstructor: () -> BookmarkViewModel
}

class BookmarkActivity : ComponentActivity() {

    private val viewModelConstructor by lazy{
        BookmarkActivityComponent::class.create(
            androidContext.applicationComponent
        ).bookmarkViewModelConstructor
    }

    private val viewModel by viewModels<BookmarkViewModel>(viewModelConstructor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookmarkRoute(ComposeBookmarkUiState(viewModel), viewModel)
        }
    }
}