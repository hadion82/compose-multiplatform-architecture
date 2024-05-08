package com.example.kmp.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.example.data.model.CharacterItem
import com.example.kmp.ui.common.CharacterContent
import com.example.kmp.ui.theme.DefaultSurface
import composemultiplatform.app.generated.resources.Res
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TitleBar() {
    Box(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.label_bookmarks),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp, 2.dp)
        )
    }
}

@Composable
fun BookmarkContents(
    pagingDataState: State<Flow<PagingData<CharacterItem>>?>,
    onThumbnailClick: (url: String?) -> Unit,
    onBookmarkClick: (item: CharacterItem) -> Unit
) {
    val pagingDataStateValue by pagingDataState
    val pagingItems = pagingDataStateValue?.collectAsLazyPagingItems() ?: return
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { position ->
            pagingItems[position]?.let { characterItem ->
                CharacterContent(
                    characterItem,
                    onThumbnailClick = onThumbnailClick,
                    onBookmarkClick = onBookmarkClick
                )
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SnackBarMessage(
    messageState: State<Action.Message?>,
    snackBarHostState: SnackbarHostState
) {
    val stateValue = messageState.value ?: return
    val scope = rememberCoroutineScope()
    val message = stringResource(
        when (stateValue) {
            is Action.Message.FailedToLoadData -> Res.string.failed_to_load_data
            is Action.Message.FailedToRemoveBookmark -> Res.string.failed_to_delete_bookmark
            is Action.Message.SuccessToSaveImage -> Res.string.image_saved_successfully
            is Action.Message.FailedToSaveImage -> Res.string.failed_to_save_image
        }
    )
    scope.launch {
        snackBarHostState
            .showSnackbar(message = message, duration = SnackbarDuration.Short)
    }
}

@Composable
fun BookmarkScreen(
    uiState: BookmarkComposableUiState,
    presenter: BookmarkPresenter
) {
    MaterialTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) {
                Column(
                    Modifier.fillMaxSize().statusBarsPadding()
                ) {
                    TitleBar()
                    BookmarkContents(
                        uiState.pagingData,
                        presenter::onThumbnailClick,
                        presenter::onBookmarkClick
                    )
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}