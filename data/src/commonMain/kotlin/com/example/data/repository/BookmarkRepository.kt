package com.example.data.repository


import app.cash.paging.PagingData
import com.example.data.database.Character
import com.example.data.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    @Throws(Exception::class)
    suspend fun addBookmark(id: Int)

    @Throws(Exception::class)
    suspend fun removeBookmark(id: Int)

    fun loadPagingBookmarks(): Flow<PagingData<Character>>
}