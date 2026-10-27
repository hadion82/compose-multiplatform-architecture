package com.example.feature.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.core.ui.extensions.collectAsStateWithMultiplatform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UiState {
    val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
    val message: StateFlow<Action.Message?>
}

class MutableMainUiState(
    private val _pagingData: MutableStateFlow<Flow<PagingData<CharacterItem>>?>,
    private val _message: MutableStateFlow<Action.Message?>
) : UiState {
    companion object {
        fun idle() = MutableMainUiState(
            MutableStateFlow(null),
            MutableStateFlow(null)
        )
    }

    override val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
        get() = _pagingData
    override val message: StateFlow<Action.Message?>
        get() = _message

    suspend fun setPagingData(pagingData: Flow<PagingData<CharacterItem>>) {
        _pagingData.emit(pagingData)
    }

    suspend fun setMessage(message: Action.Message) {
        _message.emit(message)
    }

}

//XML UI State
open class MainUiState(
    val pagingData: State<Flow<PagingData<CharacterItem>>?>,
    val message: State<Action.Message?>
)

//Compose UI State
class MainComposableUiState(
    pagingData: State<Flow<PagingData<CharacterItem>>?>,
    message: State<Action.Message?>,
    val snackBarHostState: SnackbarHostState
) : MainUiState(pagingData, message)

@Composable
fun ComposeMainUiState(uiState: UiState): MainComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithMultiplatform()
    val messageState = uiState.message.collectAsStateWithMultiplatform()
    val snackBarHostState = remember { SnackbarHostState() }
    return remember(uiState) {
        MainComposableUiState(pagingDataState, messageState, snackBarHostState)
    }
}
