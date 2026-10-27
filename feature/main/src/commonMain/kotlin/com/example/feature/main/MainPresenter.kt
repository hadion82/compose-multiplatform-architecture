package com.example.feature.main

import androidx.compose.runtime.Composable
import com.example.domain.model.CharacterItem
import com.example.shared.dispatcher.IntentDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow

interface CommonMainPresenter {
    fun onTitleClick()
    fun onBookmarkClick(item: CharacterItem)
    fun showMessage(message: String)
}

interface MultiPlatformMainPresenterDelegate {
    fun onThumbnailClick(url: String?)
}

interface MainPresenter : CommonMainPresenter, MultiPlatformMainPresenterDelegate

class MainPresenterImpl(
    private val dispatcher: MainScopedDispatcher,
    presenterDelegate: MultiPlatformMainPresenterDelegate,
    private val onOpenBookmarks: () -> Unit
) : MainPresenter, MultiPlatformMainPresenterDelegate by presenterDelegate,
    IntentDispatcher<Intention> by dispatcher {

    override fun onTitleClick() {
        onOpenBookmarks()
    }

    override fun onBookmarkClick(item: CharacterItem) {
        dispatch(
            if (item.mark) Intention.Event.AddBookmark(item.id)
            else Intention.Event.RemoveBookmark(item.id)
        )
    }

    override fun showMessage(message: String) {
        dispatch(
            Intention.Event.SnackBarMessage(message)
        )
    }
}

@Composable
expect fun ComposeMainPresenter(
    intents: MutableSharedFlow<Intention>,
    onOpenBookmarks: () -> Unit
): MainPresenter
