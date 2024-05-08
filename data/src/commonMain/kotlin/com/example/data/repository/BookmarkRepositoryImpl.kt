package com.example.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.example.data.database.Character
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class BookmarkRepositoryImpl (
    private val dataSource: BookmarkLocalDataSourceImpl
) : BookmarkRepository, BookmarkLocalDataSource by dataSource {

    override suspend fun addBookmark(id: Int) {
        dataSource.addMark(id)
    }

    override suspend fun removeBookmark(id: Int) {
        dataSource.removeMark(id)
    }

    override fun loadPagingBookmarks(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),
            pagingSourceFactory = {
                dataSource.selectBookmarks()
            }
        ).flow
}