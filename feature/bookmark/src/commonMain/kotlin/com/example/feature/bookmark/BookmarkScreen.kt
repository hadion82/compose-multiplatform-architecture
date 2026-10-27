package com.example.feature.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.model.CharacterItem
import com.example.core.ui.component.CharacterContent
import com.example.core.ui.theme.DefaultSurface
import com.example.core.ui.resources.Res
import com.example.core.ui.resources.label_back
import com.example.core.ui.resources.failed_to_delete_bookmark
import com.example.core.ui.resources.failed_to_load_data
import com.example.core.ui.resources.failed_to_save_image
import com.example.core.ui.resources.image_saved_successfully
import com.example.core.ui.resources.label_bookmarks
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun TitleBar(onBack: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Text(stringResource(Res.string.label_back))
        }
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

@Composable
fun SnackBarMessage(
    messageState: State<Action.Message?>,
    snackBarHostState: SnackbarHostState
) {
    val stateValue = messageState.value ?: return
    val message = stringResource(
        when (stateValue) {
            is Action.Message.FailedToLoadData -> Res.string.failed_to_load_data
            is Action.Message.FailedToRemoveBookmark -> Res.string.failed_to_delete_bookmark
            is Action.Message.SuccessToSaveImage -> Res.string.image_saved_successfully
            is Action.Message.FailedToSaveImage -> Res.string.failed_to_save_image
        }
    )
    LaunchedEffect(stateValue) {
        snackBarHostState
            .showSnackbar(message = message, duration = SnackbarDuration.Short)
    }
}

@Composable
fun BookmarkScreen(
    uiState: BookmarkComposableUiState,
    presenter: BookmarkPresenter,
    onBack: () -> Unit
) {
    MaterialTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) { contentPadding ->
                Column(
                    Modifier.fillMaxSize().padding(contentPadding)
                ) {
                    TitleBar(onBack)
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
