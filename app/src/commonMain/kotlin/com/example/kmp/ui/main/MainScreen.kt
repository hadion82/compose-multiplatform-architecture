package com.example.kmp.ui.main

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.PagingData
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.example.data.model.CharacterItem
import com.example.kmp.di.scope.UiScope
import com.example.kmp.ui.common.CharacterContent
import com.example.kmp.ui.theme.DefaultSurface
import com.example.shared.dispatcher.ScopedDispatcher
import composemultiplatform.app.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

interface MainScreenScope : UiScope<Intention>

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
    val pullToRefreshState = rememberPullToRefreshState()
    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    if (pullToRefreshState.isRefreshing) pagingItems.refresh()

    Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        CharacterLazyColum(
            pullToRefreshState = pullToRefreshState,
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
            pullToRefreshState = pullToRefreshState,
            modifier = Modifier.align(Alignment.Center),
            showMessage = presenter::showMessage
        )

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = pullToRefreshState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterLazyColum(
    pullToRefreshState: PullToRefreshState,
    pagingItems: LazyPagingItems<CharacterItem>,
    onThumbnailClick: (String?) -> Unit,
    onBookmarkClick: (CharacterItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        if (pullToRefreshState.isRefreshing.not()) {
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

        is LoadState.Error -> loadState.error.message?.let { showMessage(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshIndicator(
    loadState: LoadState,
    pullToRefreshState: PullToRefreshState,
    modifier: Modifier,
    showMessage: (String) -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            if (pullToRefreshState.isRefreshing.not()) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = modifier
                )
            }
        }

        is LoadState.Error -> loadState.error.message?.let { showMessage(it) }
        is LoadState.NotLoading -> pullToRefreshState.endRefresh()
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
    val message = when (stateValue) {
        is Action.Message.FailedToLoadData -> stringResource(Res.string.failed_to_load_data)
        is Action.Message.FailedToBookmark -> stringResource(Res.string.failed_to_bookmark)
        is Action.Message.FailedToDeleteBookmark -> stringResource(Res.string.failed_to_delete_bookmark)
        is Action.Message.SuccessToSaveImage -> stringResource(Res.string.image_saved_successfully)
        is Action.Message.FailedToSaveImage -> stringResource(Res.string.failed_to_save_image)
        is Action.Message.ShowMessage -> stateValue.message
    }
    scope.launch {
        snackBarHostState
            .showSnackbar(message = message, duration = SnackbarDuration.Short)
    }
}

@OptIn(ExperimentalResourceApi::class)
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
            }) {
                Column(
                    Modifier.fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) {
                    TitleBar(presenter::onTitleClick)
                    MainContents(uiState.pagingData, presenter)
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}

@Composable
expect fun Navigation(state: State<Action.Navigation?>)