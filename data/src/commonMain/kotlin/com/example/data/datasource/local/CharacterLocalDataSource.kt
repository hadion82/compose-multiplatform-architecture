package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.data.database.Character

interface CharacterLocalDataSource {

    suspend fun insertOrReplace(values: List<Character>)

    suspend fun update(values: List<Character>)

    suspend fun selectIds(ids: List<Int>): List<Int>

    suspend fun selectBookmarksById(ids: List<Int>): List<Character>

    fun selectCharacters(): PagingSource<Int, Character>
}