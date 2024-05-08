package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.data.dao.CharacterDao
import com.example.data.dao.CharacterDaoImpl
import com.example.data.database.Character
import me.tatarka.inject.annotations.Inject

//@SingleToneScope
@Inject
class CharacterLocalDataSourceImpl (
    private val dao: CharacterDaoImpl
) : CharacterLocalDataSource {

    override suspend fun insertOrReplace(values: List<Character>) =
        dao.insertOrReplace(values)

    override suspend fun update(values: List<Character>) =
        dao.update(values)

    override suspend fun selectIds(ids: List<Int>): List<Int> =
        dao.selectIds(ids)

    override suspend fun selectBookmarksById(ids: List<Int>): List<Character> =
        dao.selectCharactersById(ids)

    override fun selectCharacters(): PagingSource<Int, Character> =
        dao.selectCharacters()
}