package com.example.kmp.ui.main

import app.cash.paging.PagingData
import com.example.data.model.CharacterItem
import com.example.shared.di.scope.SingleToneScope
import com.example.shared.reducer.ActionReducer
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

sealed interface Intention {
    sealed interface Effect : Intention {
        data object LoadCharacters : Effect

        data class SaveThumbnail(val path: String?) : Effect
    }

    sealed interface Event : Intention {
        data class AddBookmark(val id: Int) : Event
        data class RemoveBookmark(val id: Int) : Event
        data class SnackBarMessage(val message: String) : Event
        sealed interface Navigation : Event {
            class OpenBookmark : Navigation
        }
    }
}

sealed interface Action {

    data class LoadPagingData(
        val data: Flow<PagingData<CharacterItem>>
    ) : Action {
        override suspend fun reduce(state: MutableMainUiState) =
            state.setPagingData(data)
    }

    sealed interface Message : Action {
        class FailedToBookmark : Message
        class FailedToDeleteBookmark : Message
        class SuccessToSaveImage : Message
        class FailedToSaveImage : Message
        class FailedToLoadData : Message
        data class ShowMessage(val message: String) : Message

        override suspend fun reduce(state: MutableMainUiState) =
            state.setMessage(this)
    }

    sealed interface Navigation : Action {
        class OpenBookmark : Navigation {
            override suspend fun reduce(state: MutableMainUiState) {
                state.setNavigation(this)
            }
        }
    }

    suspend fun reduce(state: MutableMainUiState)
}

interface MainActionReducer : ActionReducer<UiState, Action>

@SingleToneScope
@Inject
class MainActionReducerImpl : MainActionReducer {

    private val state: MutableMainUiState = MutableMainUiState.idle()

    override suspend fun reduce(action: Action) {
        Napier.d("action -> $action")
        action.reduce(state)
    }

    override fun state(): UiState = state
}

