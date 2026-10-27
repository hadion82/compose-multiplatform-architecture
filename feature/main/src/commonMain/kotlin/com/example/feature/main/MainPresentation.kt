package com.example.feature.main

import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.shared.di.scope.ViewModelScope
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

    suspend fun reduce(state: MutableMainUiState)
}

interface MainActionReducer : ActionReducer<UiState, Action>

@ViewModelScope
@Inject
class MainActionReducerImpl : MainActionReducer {

    private val state: MutableMainUiState = MutableMainUiState.idle()

    override suspend fun reduce(action: Action) {
        Napier.d("action -> $action")
        action.reduce(state)
    }

    override fun state(): UiState = state
}

