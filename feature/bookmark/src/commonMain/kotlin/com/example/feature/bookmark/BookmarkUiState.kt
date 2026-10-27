package com.example.feature.bookmark

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.core.ui.extensions.collectAsStateWithMultiplatform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UiState {
    val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
    val message: StateFlow<Action.Message?>
}

//XML UI State
open class BookmarkUiState(
    val pagingData: State<Flow<PagingData<CharacterItem>>?>,
    val message: State<Action.Message?>
)

//Compose UI State
class BookmarkComposableUiState(
    pagingData: State<Flow<PagingData<CharacterItem>>?>,
    message: State<Action.Message?>,
    val snackBarHostState: SnackbarHostState
) : BookmarkUiState(pagingData, message)

@Composable
fun ComposeBookmarkUiState(uiState: UiState): BookmarkComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithMultiplatform()
    val messageState = uiState.message.collectAsStateWithMultiplatform()
    val snackBarHostState = remember { SnackbarHostState() }

    return remember(uiState) {
        BookmarkComposableUiState(
            pagingDataState, messageState, snackBarHostState
        )
    }
}
