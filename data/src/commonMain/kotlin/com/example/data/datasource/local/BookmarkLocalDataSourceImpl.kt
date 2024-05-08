package com.example.data.datasource.local

import app.cash.paging.PagingSource
import com.example.data.dao.CharacterDao
import com.example.data.dao.CharacterDaoImpl
import com.example.data.database.Character
import me.tatarka.inject.annotations.Inject

@Inject
class BookmarkLocalDataSourceImpl (
    private val dao: CharacterDaoImpl
) : BookmarkLocalDataSource {

    override suspend fun addMark(id: Int) =
        dao.updateMark(id, true)

    override suspend fun removeMark(id: Int) =
        dao.updateMark(id, false)

    override suspend fun selectBookmarksById(ids: List<Int>): List<Character> =
        dao.selectCharactersById(ids)

    override fun selectBookmarks(): PagingSource<Int, Character> =
        dao.selectBookmarks()
}