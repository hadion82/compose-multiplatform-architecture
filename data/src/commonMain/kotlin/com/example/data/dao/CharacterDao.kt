package com.example.data.dao

import app.cash.paging.PagingSource
import com.example.data.database.Character
import com.example.data.database.LocalDatabase

interface CharacterDao {

    suspend fun insertOrReplace(values: List<Character>)

    suspend fun update(values: List<Character>)

    suspend fun updateMark(id: Int, mark: Boolean)

    suspend fun selectIds(ids: List<Int>): List<Int>

    suspend fun selectCharactersById(ids: List<Int>): List<Character>

    fun selectCharacters(): PagingSource<Int, Character>

    fun selectBookmarks(): PagingSource<Int, Character>
}