package com.example.domain.usecase.bookmark

import com.example.data.repository.BookmarkRepository
import com.example.shared.di.Dispatcher
import com.example.shared.interaction.SuspendingUseCase
import me.tatarka.inject.annotations.Inject

@Inject
class AddBookmarkUseCase (
    dispatcher: Dispatcher.IO,
    private val repository: BookmarkRepository
) : SuspendingUseCase<AddBookmarkUseCase.Params, Unit>(dispatcher.dispatcher) {

    override suspend fun execute(params: Params) =
        repository.addBookmark(params.id)

    data class Params(val id: Int)
}