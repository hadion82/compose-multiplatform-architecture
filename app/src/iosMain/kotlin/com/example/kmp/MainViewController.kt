package com.example.kmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.example.feature.bookmark.BookmarkViewModel
import com.example.feature.main.MainViewModel
import com.example.kmp.di.ApplicationComponent
import com.example.kmp.di.BookmarkViewControllerComponent
import com.example.kmp.di.MainViewControllerComponent
import com.example.kmp.di.create
import com.example.kmp.imageloader.createImageLoader
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

private class IosAppViewModelFactory(
    private val component: ApplicationComponent
) : AppViewModelFactory {
    override fun createMainViewModel(): MainViewModel =
        MainViewControllerComponent.create(component).mainViewModel

    override fun createBookmarkViewModel(): BookmarkViewModel =
        BookmarkViewControllerComponent.create(component).bookmarkViewModel
}

@OptIn(ExperimentalCoilApi::class)
fun MainViewController() = ComposeUIViewController {
    val viewModelFactory = remember {
        Napier.base(DebugAntilog())
        IosAppViewModelFactory(ApplicationComponent.create())
    }
    setSingletonImageLoaderFactory { context -> createImageLoader(context) }
    App(viewModelFactory)
}
