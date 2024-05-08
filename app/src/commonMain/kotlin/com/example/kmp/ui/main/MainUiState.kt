package com.example.kmp.ui.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.data.model.CharacterItem
import com.example.kmp.extensions.collectAsStateWithMultiplatform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UiState {
    val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
    val message: StateFlow<Action.Message?>
    val navigation: StateFlow<Action.Navigation?>
}

class MutableMainUiState(
    private val _pagingData: MutableStateFlow<Flow<PagingData<CharacterItem>>?>,
    private val _message: MutableStateFlow<Action.Message?>,
    private val _navigation: MutableStateFlow<Action.Navigation?>
) : UiState {
    companion object {
        fun idle() = MutableMainUiState(
            MutableStateFlow(null),
            MutableStateFlow(null),
            MutableStateFlow(null)
        )
    }

    override val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
        get() = _pagingData
    override val message: StateFlow<Action.Message?>
        get() = _message
    override val navigation: StateFlow<Action.Navigation?>
        get() = _navigation

    suspend fun setPagingData(pagingData: Flow<PagingData<CharacterItem>>) {
        _pagingData.emit(pagingData)
    }

    suspend fun setMessage(message: Action.Message) {
        _message.emit(message)
    }

    suspend fun setNavigation(action: Action.Navigation) {
        _navigation.emit(action)
    }
}

//XML UI State
open class MainUiState(
    val pagingData: State<Flow<PagingData<CharacterItem>>?>,
    val message: State<Action.Message?>,
    val navigation: State<Action.Navigation?>
)

//Compose UI State
class MainComposableUiState(
    pagingData: State<Flow<PagingData<CharacterItem>>?>,
    message: State<Action.Message?>,
    navigation: State<Action.Navigation?>,
    val snackBarHostState: SnackbarHostState
) : MainUiState(pagingData, message, navigation)

@Composable
fun ComposeMainUiState(uiState: UiState): MainComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithMultiplatform()
    val messageState = uiState.message.collectAsStateWithMultiplatform()
    val navigationState = uiState.navigation.collectAsStateWithMultiplatform()
    val snackBarHostState = remember { SnackbarHostState() }
    return remember {
        MainComposableUiState(pagingDataState, messageState, navigationState, snackBarHostState)
    }
}