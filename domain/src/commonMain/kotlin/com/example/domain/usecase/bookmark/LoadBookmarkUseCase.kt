package com.example.domain.usecase.bookmark

import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.domain.repository.BookmarkRepository
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class LoadBookmarkUseCase (
    private val repository: BookmarkRepository
) : UseCase<Unit, Flow<PagingData<CharacterItem>>> {

    override fun execute(params: Unit): Flow<PagingData<CharacterItem>> =
        repository.loadPagingBookmarks()
}
