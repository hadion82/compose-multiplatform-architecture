package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.data.database.Character

interface BookmarkLocalDataSource {

    suspend fun addMark(id: Int)

    suspend fun removeMark(id: Int)

    suspend fun selectBookmarksById(ids: List<Int>): List<Character>

    fun selectBookmarks(): PagingSource<Int, Character>
}