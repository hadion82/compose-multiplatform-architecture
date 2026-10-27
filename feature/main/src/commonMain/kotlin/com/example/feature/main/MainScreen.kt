package com.example.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.model.CharacterItem
import com.example.core.ui.component.CharacterContent
import com.example.core.ui.theme.DefaultSurface
import com.example.shared.dispatcher.ScopedDispatcher
import com.example.core.ui.resources.Res
import com.example.core.ui.resources.failed_to_bookmark
import com.example.core.ui.resources.failed_to_delete_bookmark
import com.example.core.ui.resources.failed_to_load_data
import com.example.core.ui.resources.failed_to_save_image
import com.example.core.ui.resources.image_saved_successfully
import com.example.core.ui.resources.label_bookmark
import com.example.core.ui.resources.marvel_characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.compose.resources.stringResource

class MainScopedDispatcher(
    intent: MutableSharedFlow<Intention>,
    scope: CoroutineScope
) :
    ScopedDispatcher<Intention>(intent, scope)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContents(mainState: State<Flow<PagingData<CharacterItem>>?>, presenter: MainPresenter) {
    val pagingData by mainState
    val pagingItems = pagingData?.collectAsLazyPagingItems() ?: return
    val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = pagingItems::refresh,
        modifier = Modifier.fillMaxSize()
    ) {
        CharacterLazyColum(
            pagingItems = pagingItems,
            onThumbnailClick = presenter::onThumbnailClick,
            onBookmarkClick = presenter::onBookmarkClick
        )
        AppendIndicator(
            loadState = pagingItems.loadState.append,
            showMessage = presenter::showMessage
        )
        RefreshIndicator(
            loadState = pagingItems.loadState.refresh,
            modifier = Modifier.align(Alignment.Center),
            showMessage = presenter::showMessage
        )
    }
}

@Composable
fun CharacterLazyColum(
    pagingItems: LazyPagingItems<CharacterItem>,
    onThumbnailClick: (String?) -> Unit,
    onBookmarkClick: (CharacterItem) -> Unit
) {
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
                    character = characterItem,
                    onThumbnailClick = onThumbnailClick,
                    onBookmarkClick = onBookmarkClick
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun AppendIndicator(
    loadState: LoadState,
    showMessage: (String) -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {/*Nothing*/
        }

        is LoadState.NotLoading -> { /*Nothing*/
        }

        is LoadState.Error -> LaunchedEffect(loadState.error) {
            loadState.error.message?.let(showMessage)
        }
    }
}

@Composable
fun RefreshIndicator(
    loadState: LoadState,
    modifier: Modifier,
    showMessage: (String) -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            CircularProgressIndicator(modifier = modifier)
        }

        is LoadState.Error -> LaunchedEffect(loadState.error) {
            loadState.error.message?.let(showMessage)
        }
        is LoadState.NotLoading -> Unit
    }
}

@Composable
fun SnackBarMessage(
    messageState: State<Action.Message?>,
    snackBarHostState: SnackbarHostState
) {
    val stateValue = messageState.value ?: return
    val message = when (stateValue) {
        is Action.Message.FailedToLoadData -> stringResource(Res.string.failed_to_load_data)
        is Action.Message.FailedToBookmark -> stringResource(Res.string.failed_to_bookmark)
        is Action.Message.FailedToDeleteBookmark -> stringResource(Res.string.failed_to_delete_bookmark)
        is Action.Message.SuccessToSaveImage -> stringResource(Res.string.image_saved_successfully)
        is Action.Message.FailedToSaveImage -> stringResource(Res.string.failed_to_save_image)
        is Action.Message.ShowMessage -> stateValue.message
    }
    LaunchedEffect(stateValue) {
        snackBarHostState
            .showSnackbar(message = message, duration = SnackbarDuration.Short)
    }
}

@Composable
fun TitleBar(onTitleClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.marvel_characters),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(4.dp, 2.dp),
            onClick = onTitleClick
        ) {
            Text(text = stringResource(Res.string.label_bookmark))
        }
    }
}

@Composable
fun MainScreen(
    uiState: MainComposableUiState, presenter: MainPresenter
) {
    MaterialTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) { contentPadding ->
                Column(
                    Modifier.fillMaxSize().padding(contentPadding)
                ) {
                    TitleBar(presenter::onTitleClick)
                    MainContents(uiState.pagingData, presenter)
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}
