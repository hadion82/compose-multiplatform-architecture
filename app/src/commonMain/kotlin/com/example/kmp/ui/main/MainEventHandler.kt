package com.example.kmp.ui.main

import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.shared.hanlder.AbstractActionHandler
import me.tatarka.inject.annotations.Inject

@Inject
class MainEventHandler(
    dispatcher: MainActionDispatcher,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : AbstractActionHandler<Intention.Event>(),
    MainActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Event) {
        when (intent) {
            is Intention.Event.AddBookmark ->
                addBookmarkUseCase(AddBookmarkUseCase.Params(intent.id))
                    .onFailure { dispatch(Action.Message.FailedToBookmark()) }

            is Intention.Event.RemoveBookmark ->
                removeBookmarkUseCase(RemoveBookmarkUseCase.Params(intent.id))
                    .onFailure { dispatch(Action.Message.FailedToDeleteBookmark()) }

            is Intention.Event.SnackBarMessage ->
                dispatch(Action.Message.ShowMessage(intent.message))

            is Intention.Event.Navigation -> executeNavigation(intent)
        }
    }

    private suspend fun executeNavigation(intent: Intention.Event.Navigation) {
        when (intent) {
            is Intention.Event.Navigation.OpenBookmark ->
                dispatch(Action.Navigation.OpenBookmark())
        }
    }
}