package com.example.kmp.ui.bookmark

import app.cash.paging.PagingData
import com.example.data.model.CharacterItem
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.shared.platform.viewmodel.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class BookmarkViewModel(
    loadBookmarkUseCase: LoadBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val saveThumbnailUseCase: SaveThumbnailUseCase,
) : ViewModel(), UiState, BookmarkPresenter {

    private val _pagingData: MutableStateFlow<Flow<PagingData<CharacterItem>>?> =
        MutableStateFlow(null)
    override val pagingData: StateFlow<Flow<PagingData<CharacterItem>>?>
        get() = _pagingData

    private val _message: MutableStateFlow<Action.Message?> = MutableStateFlow(null)
    override val message: StateFlow<Action.Message?>
        get() = _message

    init {
        viewModelScope.launch {
            loadBookmarkUseCase(Unit)
                .onSuccess { _pagingData.emit(it) }
                .onFailure { _message.emit(Action.Message.FailedToLoadData()) }
        }
    }

    override fun onThumbnailClick(url: String?) {
        viewModelScope.launch {
            saveThumbnailUseCase(SaveThumbnailUseCase.Params(url))
                .onSuccess {
                    _message.emit(Action.Message.SuccessToSaveImage())
                }
                .onFailure {
                    _message.emit(Action.Message.FailedToSaveImage())
                }
        }
    }

    override fun onBookmarkClick(item: CharacterItem) {
        viewModelScope.launch {
            removeBookmarkUseCase(RemoveBookmarkUseCase.Params(item.id))
                .onFailure { _message.emit(Action.Message.FailedToRemoveBookmark()) }
        }
    }
}