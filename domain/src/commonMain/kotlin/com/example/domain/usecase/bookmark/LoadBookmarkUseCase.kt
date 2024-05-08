package com.example.domain.usecase.bookmark

import app.cash.paging.PagingData
import app.cash.paging.map
import com.example.data.model.CharacterItem
import com.example.data.repository.BookmarkRepository
import com.example.domain.mapper.CharacterItemMapper
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class LoadBookmarkUseCase (
    private val repository: BookmarkRepository,
    private val mapper: CharacterItemMapper
) : UseCase<Unit, Flow<PagingData<CharacterItem>>> {

    override fun execute(params: Unit): Flow<PagingData<CharacterItem>> =
        repository.loadPagingBookmarks().map { it.map(mapper) }
}