package com.example.data.repository


import app.cash.paging.PagingData
import com.example.data.database.Character
import com.example.data.entity.BookmarkEntity
import com.example.data.model.CharacterItem
import com.example.data.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    @Throws(Exception::class)
    fun loadPagingData(): Flow<PagingData<Character>>
}