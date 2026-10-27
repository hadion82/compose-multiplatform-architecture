package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.mapper.CharacterItemMapper
import com.example.domain.model.CharacterItem
import com.example.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class BookmarkRepositoryImpl (
    private val dataSource: BookmarkLocalDataSourceImpl,
    private val mapper: CharacterItemMapper
) : BookmarkRepository, BookmarkLocalDataSource by dataSource {

    override suspend fun addBookmark(id: Int) {
        dataSource.addMark(id)
    }

    override suspend fun removeBookmark(id: Int) {
        dataSource.removeMark(id)
    }

    override fun loadPagingBookmarks(): Flow<PagingData<CharacterItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = {
                dataSource.selectBookmarks()
            }
        ).flow.map { pagingData -> pagingData.map(mapper) }
}
